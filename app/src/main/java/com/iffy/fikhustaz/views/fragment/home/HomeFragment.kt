package com.iffy.fikhustaz.views.fragment.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.Ustad
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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

    override fun showLoad() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoad() {
        progressBar.visibility = View.GONE
    }

    override fun setData(ustad: Ustad) {
        name_tv_home.text = currentUser?.displayName
        datebirt_tv_home.text = "${ustad.tempatLahir}, ${ustad.tanggalLahir}"
        pendidikan_tv_home.text = ustad.pendidikan
        keilmuan_tv_home.text = ustad.keilmuan
        firkah_tv_home.text = ustad.firkah
        mazhab_tv_home.text = ustad.mazhab
    }
}
