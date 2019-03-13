package com.iffy.fikhustaz.activity.register

import com.iffy.fikhustaz.data.Verify
import com.iffy.fikhustaz.util.FirebaseUtil

class RegisterPresenter (v: RegisterContract.View) : RegisterContract.Presenter {

    private var view: RegisterContract.View? = v


    override fun verifyEntries(data: Verify) : Boolean{
        if (data.uname.isEmpty()){
            view?.showMsg("Username harus diisi")
            return false
        }
        if (data.uname.length < 3){
            view?.showMsg("Username minimal 3 karakter")
            return false
        }
        if (data.name.isEmpty()){
            view?.showMsg("Nama harus diisi")
            return false
        }
        if (data.name.length < 3){
            view?.showMsg("Nama minimal 3 karakter")
            return false
        }
        if (data.email.isEmpty()){
            view?.showMsg("Email harus diisi")
            return false
        }
        if (data.phone.isEmpty()){
            view?.showMsg("Nomor harus diisi")
            return false
        }
        if (data.phone.length < 10){
            view?.showMsg("Nomor minimal 10 digit")
            return false
        }
        if (data.pass.isEmpty()){
            view?.showMsg("Password harus diisi")
            return false
        }
        if (data.pass != data.conf){
            view?.showMsg("Password tidak sama")
            return false
        }

        return true

    }

    override fun saveData(data: Verify): Boolean {
        view?.showLoading()
        FirebaseUtil
        view?.hideLoading()
        view?.showMsg("Success")
        return true
    }

}