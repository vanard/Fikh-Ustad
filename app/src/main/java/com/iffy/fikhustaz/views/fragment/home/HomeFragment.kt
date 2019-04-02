package com.iffy.fikhustaz.views.fragment.home

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log.d
import android.view.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.Ustad
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.iffy.fikhustaz.views.activity.editprof.EditProfileActivity
import com.iffy.fikhustaz.views.activity.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.startActivity

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var presenter:HomePresenter
    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    private lateinit var dialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
        presenter = HomePresenter(this)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).supportActionBar?.title = "Home"
        presenter.getData()

        edit_btn_home.setOnClickListener {
            startActivity<EditProfileActivity>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when(item.itemId){
            R.id.menu_logout ->{
                mAuth.signOut()
                startActivity(intentFor<LoginActivity>().newTask().clearTask())
                true
            }
            else -> super.onOptionsItemSelected(item)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.cancelGetData()
    }

    override fun showLoad() {
        dialog = ProgressDialog.show(this@HomeFragment.context, "", "Please wait")
        dialog.setCancelable(false)
        dialog.isIndeterminate
    }

    override fun hideLoad() {
        dialog.dismiss()
    }

    override fun setData(ustad: Ustad) {

        name_tv_home.text = currentUser?.displayName ?: "Belum ada informasi"
        datebirt_tv_home.text = "${ustad.tempatLahir}, ${ustad.tanggalLahir}"
        pendidikan_tv_home.text = ustad.pendidikan ?: "Belum ada informasi"
        keilmuan_tv_home.text = ustad.keilmuan ?: "Belum ada informasi"
        firkah_tv_home.text = ustad.firkah ?: "Belum ada informasi"
        mazhab_tv_home.text = ustad.mazhab ?: "Belum ada informasi"

        if (name_tv_home.text.isEmpty())
            name_tv_home.text = "Belum ada informasi"
        if (datebirt_tv_home.text == ", "){
            datebirt_tv_home.text = "Belum ada informasi"
        }
        if (pendidikan_tv_home.text.isEmpty())
            pendidikan_tv_home.text = "Belum ada informasi"
        if (keilmuan_tv_home.text.isEmpty())
            keilmuan_tv_home.text = "Belum ada informasi"
        if (firkah_tv_home.text.isEmpty())
            firkah_tv_home.text = "Belum ada informasi"
        if (mazhab_tv_home.text.isEmpty())
            mazhab_tv_home.text = "Belum ada informasi"

    }
}
