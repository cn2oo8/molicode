var _ = require('underscore')

export function getConfigObject(configList, configKey) {
    if (_.isEmpty(configList)) {
        return {};
    }
    for (var i = 0; i < configList.length; i++) {
        var config = configList[i];
        if (config.configKey === configKey) {
            return JSON.parse(config.configValue);
        }
    }
    return {};
}

export function getConfigObjectVal(configList, configKey, key) {
    var configObject = getConfigObject(configList, configKey);
    return configObject[key];
}