//import cfs from '@/utils/configs.js'

export default {
    user: {
        add: {
            age: [{type: 'integer', required: true, message: 'age不能为空,且为整形！', trigger: 'blur'}],
            address: [{required: true, message: 'address不能为空', trigger: 'blur'}],
            name: [{required: true, message: 'name不能为空,长度为5~20', trigger: 'blur', min: 5, max: 20}]
        }
    }
}