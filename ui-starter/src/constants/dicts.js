const dictKinds = {
    STD_STATUS: 'dict_std_status',
    STD_GENDER: 'dict_std_gender',
    PROJECT_TYPE_DICT: 'project_type_dict',
    DATABASE_NAME_DICT: 'database_name_dict',
    STD_BOOLEAN_DICT: 'std_boolean_dict',
    STD_YESNO_DICT: 'std_yesno_dict',
    TEMPLATE_TYPE_DICT: 'template_type_dict',
    REPLACE_TYPE_DICT: 'replace_type_dict',
    DATA_MODEL_TYPE_DICT: 'data_model_type_dict',
    RESOURCE_TYPE_DICT: 'resource_type_dict'

}

export default {
    dictKinds,
    dictData: {
        [dictKinds.STD_STATUS]: [{
            kind: dictKinds.STD_STATUS,
            itemKey: '1',
            'itemName': '正常',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1,
            'cssType': 'green'
        }, {
            kind: dictKinds.STD_STATUS,
            itemKey: '-1',
            'itemName': '删除',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 2,
            'cssType': 'red'
        }
        ],
        [dictKinds.PROJECT_TYPE_DICT]: [{
            kind: dictKinds.PROJECT_TYPE_DICT,
            itemKey: '1',
            'itemName': '本地',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1
        }, {
            kind: dictKinds.PROJECT_TYPE_DICT,
            itemKey: '2',
            'itemName': '仓库(暂不支持)',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1
        }
        ],

        [dictKinds.DATABASE_NAME_DICT]: [{
            kind: dictKinds.DATABASE_NAME_DICT,
            itemKey: 'mysql',
            'itemName': 'MySql',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1,
            'cssType': 'blue'
        }, {
            kind: dictKinds.DATABASE_NAME_DICT,
            itemKey: 'oracle',
            'itemName': 'Oracle',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 2,
            'cssType': 'green'
        }
        ],
        [dictKinds.STD_BOOLEAN_DICT]: [{
            kind: dictKinds.STD_BOOLEAN_DICT,
            itemKey: 'true',
            'itemName': '是',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1,
            'cssType': 'blue'
        }, {
            kind: dictKinds.STD_BOOLEAN_DICT,
            itemKey: 'false',
            'itemName': '否',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1,
            'cssType': 'green'
        }
        ],
        [dictKinds.STD_YESNO_DICT]: [{
            kind: dictKinds.STD_YESNO_DICT,
            itemKey: '1',
            'itemName': '是',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1,
            'cssType': 'blue'
        }, {
            kind: dictKinds.STD_YESNO_DICT,
            itemKey: '2',
            'itemName': '否',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1,
            'cssType': 'green'
        }
        ],
        [dictKinds.TEMPLATE_TYPE_DICT]: [{
            kind: dictKinds.TEMPLATE_TYPE_DICT,
            itemKey: 'local',
            'itemName': '本地模板',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1,
            'cssType': 'blue'
        }, {
            kind: dictKinds.TEMPLATE_TYPE_DICT,
            itemKey: 'maven',
            'itemName': 'maven',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1,
            'cssType': 'green'
        }
        ],
        [dictKinds.REPLACE_TYPE_DICT]: [{
            kind: dictKinds.REPLACE_TYPE_DICT,
            itemKey: '1',
            'itemName': '全部替换，路径和内容一起替换',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1,
            'cssType': 'blue'
        }, {
            kind: dictKinds.REPLACE_TYPE_DICT,
            itemKey: '2',
            'itemName': '只替换文本',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1,
            'cssType': 'green'
        },
        {
            kind: dictKinds.REPLACE_TYPE_DICT,
            itemKey: '3',
            'itemName': '只替换目录',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1,
            'cssType': 'blue'
        }, {
            kind: dictKinds.REPLACE_TYPE_DICT,
            itemKey: '4',
            'itemName': '复制不替换',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1,
            'cssType': 'green'
        }
        ],
        [dictKinds.DATA_MODEL_TYPE_DICT]: [{
            kind: dictKinds.DATA_MODEL_TYPE_DICT,
            itemKey: 'tableModel',
            'itemName': 'tableModel表模型',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1
        }, {
            kind: dictKinds.DATA_MODEL_TYPE_DICT,
            itemKey: 'json',
            'itemName': 'JSON',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 2
        },
        {
            kind: dictKinds.DATA_MODEL_TYPE_DICT,
            itemKey: 'lineList',
            'itemName': '单行文本处理',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 3
        },
        {
            kind: dictKinds.DATA_MODEL_TYPE_DICT,
            itemKey: 'cellList',
            'itemName': '单元文本处理',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 4
        },
        {
            kind: dictKinds.DATA_MODEL_TYPE_DICT,
            itemKey: 'javaSource',
            'itemName': 'Java源码',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 5
        },
        {
            kind: dictKinds.DATA_MODEL_TYPE_DICT,
            itemKey: 'rawContent',
            'itemName': '原始内容',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 6
        }
        ],
        [dictKinds.RESOURCE_TYPE_DICT]: [{
            kind: dictKinds.RESOURCE_TYPE_DICT,
            itemKey: 'file',
            'itemName': '文件',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 1
        }, {
            kind: dictKinds.RESOURCE_TYPE_DICT,
            itemKey: 'front',
            'itemName': '前台输入',
            'parentKind': '',
            'parentKey': '',
            'sortNum': 2
        }
        ]
    }
}
