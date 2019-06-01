<template>
    <div>
        <p>
            <h2>1. 数据库所有字段及表名请全部加上中文注释！</h2>
            加上中文注释后，可以使生成的代码更加完善。
            <br/>
        </p>

        <p>
        <h2> 2. 如果启用字段备注智能识别，尤其是字典项数据.</h2>
        请按以下格式：
        <strong>
            <span class="text-info">中文名: 1.正常，2.异常, 3.未知</span>
        </strong>

        进行描述。<br>
        方便程序自动识别字典数据。
        字典数据生成后，会在 tableModel文件中，看到
        <pre>
&nbsp; &lt;dicts&gt;
&nbsp; &nbsp; &lt;dict id='contactUserItem_status_DICT' name='status字段的字典项'&gt;
&nbsp; &nbsp; &nbsp; &lt;option value='1' cssClass=''&gt;正常&lt;/option&gt;
&nbsp; &nbsp; &nbsp; &lt;option value='2' cssClass=''&gt;删除&lt;/option&gt;
&nbsp; &nbsp; &lt;/dict&gt;
&nbsp; &lt;/dicts&gt;
</pre>
        <br/>
        如果是使用公共的字典数据，可以用如下的格式： <strong><span class="text-info">中文名@dict_id</span> </strong>进行描述。<br>
        公共的字典项配置请在 <span class="text-error j_open_file" data-filetype="file" data-inputid="dictFile">config\molicode\dict.xml</span>进行配置。


        </p>
        <br>

        <p>
        <h5> 3. 字典数据的生产代码的片段</h5>
        可以在程序的：<span class="text-error j_open_file" data-filetype="file" data-inputid="snippetTemplateFile">config\molicode\snippetTemplate.xml</span>
        配置相关字典项的代码片段。
        </p>


        <br>

        <p>
        <h5> 4. 字段名的智能识别</h5>
        可以在程序的：<span class="text-error j_open_file" data-filetype="file" data-inputid="molicode_dbTableUtilFile">config\bindSource\molicode_dbTableUtil.properties</span>
        配置文件中，配置相关的智能识别字段。

        表达时中各个子匹配以逗号分隔：<br/>
        a.以 <span class="text-info"> *文本</span>格式，表示以关键词结尾匹配。<br>
        b.以 <span class="text-info"> 文本*</span>格式，表示以关键词开始匹配。<br>
        c.以 <span class="text-info"> *文本*</span>格式，只要包含该关键词即匹配。<br>
        d.在整个表达式以 <span class="text-error"> ^</span> 号开始，表示满足其中任意一个，认为不匹配，即取非操作！<br>
        <table class="table">
            <thead class="table_head">
            <tr>
                <th>属性名</th>
                <th>属性说明</th>
                <th>默认值</th>
            </tr>
            </thead>

            <tbody>
            <tr>
                <td>createTimeExp</td>
                <td>创建时间字段识别</td>
                <td>gmtCreate,created</td>
            </tr>
            <tr>
                <td>updateTimeExp</td>
                <td>修改时间字段识别</td>
                <td>gmtModify,modified</td>
            </tr>
            <tr>
                <td>queryListExp</td>
                <td>列表中的字段识别(不包含以下)</td>
                <td>^gmtCreate,created,*note,*remark,*create*</td>
            </tr>
            <tr>
                <td>addListExp</td>
                <td>新增页面字段识别(不包含以下)</td>
                <td>^gmtCreate,created,gmtModify,modified,*create*</td>
            </tr>
            <tr>
                <td>updateListExp</td>
                <td>修改页面字段识别(不包含以下)</td>
                <td>^gmtCreate,created,gmtModify,modified,*create*</td>
            </tr>
            <tr>
                <td>viewListExp</td>
                <td>查看页面字段识别</td>
                <td>空</td>
            </tr>
            <tr>
                <td>searchKeyExp</td>
                <td>查询条件字段识别</td>
                <td>*id,name,*state,*status,*type</td>
            </tr>

            </tbody>
        </table>

        </p>

    </div>
</template>

<script>
    export default {
        name: 'tableModelHelp'
    }
</script>

<style scoped>

</style>
