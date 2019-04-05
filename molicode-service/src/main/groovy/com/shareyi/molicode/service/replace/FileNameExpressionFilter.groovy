package com.shareyi.molicode.service.replace

import org.apache.commons.collections.CollectionUtils
import org.apache.commons.lang3.StringUtils

class FileNameExpressionFilter implements FileNameFilter {

    String nameExpression;
    private List<NameFilterType> filterTypes;
    boolean initialed = false;
    private boolean not = false;
    private boolean emptyMatch = true; //默认为true

    FileNameExpressionFilter(String nameExpression) {
        this.setNameExpression(nameExpression);
    }

    boolean isMatch(String name) {
        initNameFilter();
        boolean isMatch = false;
        //为空 ，默认全过
        if (CollectionUtils.isEmpty(filterTypes)) {
            return emptyMatch;
        }


        for (NameFilterType filterType : filterTypes) {
            if (filterType.getType() == 0) {
                isMatch = name.equals(filterType.getSearchText());
                if (isMatch) {
                    break;
                }
            }

            if (filterType.getType() == 1) {
                isMatch = name.endsWith(filterType.getSearchText());
                if (isMatch) {
                    break;
                }
            }

            if (filterType.getType() == 2) {
                isMatch = name.startsWith(filterType.getSearchText());
                if (isMatch) {
                    break;
                }
            }

            if (filterType.getType() == 3) {
                isMatch = name.contains(filterType.getSearchText());
                if (isMatch) {
                    break;
                }
            }
        }

        if (not) {//如果取非 只要有1个匹配上，认为失败！
            return !isMatch;
        } else {
            return isMatch;
        }
    }


    public void initNameFilter() {
        if (StringUtils.isEmpty(nameExpression) || initialed) {
            return;
        }
        if (nameExpression.charAt(0) == '^') {
            nameExpression = nameExpression.substring(1).trim();
            if (nameExpression.charAt(0) == '(' || nameExpression.charAt(0) == '（') {
                nameExpression = nameExpression.substring(1).trim();
            }
            if (nameExpression.endsWith(")") || nameExpression.endsWith("）")) {
                nameExpression = nameExpression.substring(0, nameExpression.length() - 1).trim();
            }
            not = true;
        }
        String[] filters = nameExpression.split("[,，\n]+");
        filterTypes = NameFilterType.getNameFilterType(filters);
        initialed = true;
    }

    String getNameExpression() {
        return nameExpression
    }

    void setNameExpression(String nameExpression) {
        this.nameExpression = StringUtils.trim(nameExpression)
    }

    public boolean isEmptyMatch() {
        return emptyMatch;
    }

    public void setEmptyMatch(boolean emptyMatch) {
        this.emptyMatch = emptyMatch;
    }


}
