export default {
    autoCode: {
        tableModel: {
            makeTableModel: 'autoCode/tableModel/makeTableModel',
            getTableList: 'autoCode/tableModel/getTableList'
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
            editFile: 'common/file/editFile'
        },
        bindResource: {
            getBindResource: 'common/bindResource/getBindResource',
            saveBindResource: 'common/bindResource/saveBindResource'
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
            add: 'conf/acConfig/add',
            update: 'conf/acConfig/update',
            delete: 'conf/acConfig/delete',
            save: 'conf/acConfig/save'
        },
        commonExtInfo: {
            list: 'conf/commonExtInfo/list',
            add: 'conf/commonExtInfo/add',
            update: 'conf/commonExtInfo/update',
            delete: 'conf/commonExtInfo/delete',
            save: 'conf/commonExtInfo/save',
            getByOwnerAndKey: 'conf/commonExtInfo/getByOwnerAndKey'
        }
    },
    sys: {
        system: {
            getProfileCache: 'sys/system/getProfileCache',
            getProfileInfo: 'sys/system/getProfileInfo'
        }
    }
};
