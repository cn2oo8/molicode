export default {
    autoCode: {
        tableModel: {
            makeTableModel: 'autoCode/tableModel/makeTableModel',
            getTableList: 'autoCode/tableModel/getTableList',
            getTableListBySql: 'autoCode/tableModel/getTableListBySql',
            getTableInfo: 'autoCode/tableModel/getTableInfo',
            saveTableModel: 'autoCode/tableModel/saveTableModel'
        },
        coder: {
            execute: 'autoCode/coder/execute',
            getAutoMake: 'autoCode/coder/getAutoMake'
        },
        replace: {
            execute: 'autoCode/replace/doReplace'
        },
        smartSegment: {
            execute: '/autoCode/smartSegment/execute'
        }
    },
    common: {
        file: {
            fileChooser: 'common/file/fileChooser',
            openFile: 'common/file/openFile',
            editFile: 'common/file/editFile',
            loadProjectOutputFile: 'common/file/loadProjectOutputFile'
        },
        bindResource: {
            getBindResource: 'common/bindResource/getBindResource',
            saveBindResource: 'common/bindResource/saveBindResource'
        },
        database: {
            testConnection: 'common/database/testConnection'
        }
    },
    conf: {
        acProject: {
            list: 'conf/acProject/list',
            add: 'conf/acProject/add',
            update: 'conf/acProject/update',
            delete: 'conf/acProject/delete',
            getByProjectKey: 'conf/acProject/getByProjectKey',
            copyProject: 'conf/acProject/copyProject'
        },
        acConfig: {
            list: 'conf/acConfig/list',
            save: 'conf/acConfig/save'
        },
        commonExtInfo: {
            save: 'conf/commonExtInfo/save',
            getByOwnerAndKey: 'conf/commonExtInfo/getByOwnerAndKey'
        }
    },
    sys: {
        system: {
            getProfileCache: 'sys/system/getProfileCache',
            getProfileInfo: 'sys/system/getProfileInfo'
        },
        acUser: {
            getLoginUser: 'sys/acUser/getLoginUser',
            changePassword: 'sys/acUser/changePassword',
            updateUserInfo: 'sys/acUser/updateUserInfo'
        }
    },
    loginfree: {
        login: {
            login: 'loginfree/login',
            register: 'loginfree/register'
        }
    },
    repo: {
        git: {
            fetchRepo: 'repo/git/fetchRepo'
        },
        github: {
            'baseProjectRepos': 'https://raw.githubusercontent.com/cn2oo8/molicode_template_awesome/master/api/molicode_repo/base-project-repos.json',
            'templateRepos': 'https://raw.githubusercontent.com/cn2oo8/molicode_template_awesome/master/api/molicode_repo/template-repos.json'
        }
    }
};
