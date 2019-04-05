import MockAdapter from 'axios-mock-adapter'
import constants from '@/constants/constants.js'
var _ = require('underscore')

const mockConfigList = [
  /*  {url: constants.urls.sample.user.delete, data: {'success': true, 'message': '删除成功'}},
    {url: constants.urls.sample.user.add, data: {'success': true, 'message': '新增成功'}},
    {url: constants.urls.sample.user.update, data: {'success': true, 'message': '修改成功'}}*/

]

export function mock (axios) {
    let mock = new MockAdapter(axios)

    _.each(mockConfigList, function (item) {
        mock.onPost(item.url).reply(config => {
            return new Promise((resolve, reject) => {
                resolve([200, item.data])
            })
        })
    })

}