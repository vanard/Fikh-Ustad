package com.iffy.fikhustaz.activity.login

import com.iffy.fikhustaz.data.model.Verify
import com.iffy.fikhustaz.util.FirebaseUtil

class LoginPresenter (v: LoginContract.View) : LoginContract.Presenter{
    private var view: LoginContract.View? = v

    override fun verify(data: Verify): Boolean {
        if (data.email.isEmpty()){
            view?.showMsg("Username harus diisi")
            return false
        }
        if (data.pass.isEmpty()){
            view?.showMsg("Password harus diisi")
            return false
        }

        return true

    }

    override fun login(data: Verify) : Boolean {
        view?.showLoading()
        FirebaseUtil

        return if (data.email == "vanard@vanard.com" && data.pass == "123"){
            view?.showMsg("Login Success")
            view?.hideLoading()
            true
        }else{
            view?.showMsg("Email atau Password salah")
            view?.hideLoading()
            false
        }
    }

}