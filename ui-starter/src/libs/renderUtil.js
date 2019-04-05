var moment = require('moment')
var _ = require('underscore')

/**
 * 转换日期
 * @param param
 * @returns {*}
 */
export const formatDate = (param) => {
    return formatDateTimeExp(param, 'YYYY-MM-DD')
}

/**
 * 转换日期+时间
 * @param param
 * @returns {*}
 */
export const formatDateTime = (param) => {
    return formatDateTimeExp(param, 'YYYY-MM-DD HH:mm:ss')
}

/**
 * 转换日期
 * @param param
 * @returns {*}
 */
export const formatDateTimeExp = (param, exp) => {
    if (param == null || param === '') {
        return ''
    }
    if (isNaN(param)) {
        return param
    }
    return moment(param).format(exp)
}


export const toArray = (param) => {
    if (param === null || param === undefined) {
        return []
    }
    if (_.isArray(param)) {
        return param
    }
    return [param]
}

export function toStringArray(params) {
    var array = toArray(params)
    return _.map(array, function (item) {
        if (item == null || _.isString(item)) {
            return item
        }
        return item + ''
    })
}

export function transferString (param) {
    if (_.isArray(param)) {
        return toStringArray(param)
    }
    let tempVal = param
    if (tempVal !== null && !_.isString(tempVal)) {
        tempVal = tempVal + ''
    }
    return tempVal
}

export function transferNumber (v) {
    if (_.isArray(v)) {
        v = _.map(v, function (i) {
            return Number.isNaN(Number(i)) ? i : Number(i)
        })
    } else {
        v = Number.isNaN(Number(this.model)) ? v : Number(v)
    }
    return v
}
