package com.iffy.fikhustaz.views.fragment.home

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log.d
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.itemviews.ScheduleItem
import com.iffy.fikhustaz.data.model.profile.ItSchedule
import com.iffy.fikhustaz.data.model.profile.Ustad
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.iffy.fikhustaz.views.activity.editprof.EditProfileActivity
import com.iffy.fikhustaz.views.activity.login.LoginActivity
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.startActivity

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var presenter:HomePresenter
    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    private lateinit var dialog: ProgressDialog

    private var mSchedule = mutableListOf<Item>()
    private var mScheduleList = mutableListOf<ItSchedule>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).supportActionBar?.title = "Home"

        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
        presenter = HomePresenter(this)
        setHasOptionsMenu(true)

        presenter.getData()

        rv_home.layoutManager = GridLayoutManager(this@HomeFragment.context, 3)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when(item.itemId){
            R.id.menu_logout -> {
                mAuth.signOut()
                startActivity(intentFor<LoginActivity>().newTask().clearTask())
                true
            }
            R.id.menu_edit_profile -> {
                startActivity<EditProfileActivity>()
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
        dialog = ProgressDialog.show(this@HomeFragment.context, "", "Loading data")
        dialog.setCancelable(false)
        dialog.isIndeterminate
    }

    override fun hideLoad() {
        dialog.dismiss()
    }

    override fun setData(ustad: Ustad) {

        if (FirebaseAuth.getInstance().currentUser?.photoUrl != null) {
            Picasso.get()
                .load(FirebaseAuth.getInstance().currentUser!!.photoUrl.toString())
                .into(img_home)
        }

        if (!ustad.userOnline.isNullOrEmpty()){
            if (ustad.userOnline[0].status == "Online"){
                img_home.borderColor = resources.getColor(R.color.green_app)
            }else{
                img_home.borderColor = resources.getColor(R.color.colorTextHint)
            }


        }else{
            img_home.borderColor = resources.getColor(R.color.colorTextHint)
        }

        if (ustad.rate != null){
            rate_tv_home.text = "Pesan dibalas ${ustad.rate}%"
        }

        name_tv_home.text = currentUser?.displayName ?: "Belum ada informasi"
        datebirt_tv_home.text = "${ustad.tempatLahir}, ${ustad.tanggalLahir}"
        pendidikan_tv_home.text = ustad.pendidikan ?: "Belum ada informasi"
        keilmuan_tv_home.text = ustad.keilmuan ?: "Belum ada informasi"
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
        if (mazhab_tv_home.text.isEmpty())
            mazhab_tv_home.text = "Belum ada informasi"

        if (ustad.schedule != null){
            mScheduleList.addAll(ustad.schedule)
        }

        if (mScheduleList.isNotEmpty()){
            mScheduleList.forEach {
                mSchedule.add(ScheduleItem(it))
            }

            rv_home.apply {
                adapter = GroupAdapter<ViewHolder>().apply {
                    addAll(mSchedule)
                }
            }
        }

    }
}
