package uz.bdmgroup.barakasavdo.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_header_layout.*
import kotlinx.android.synthetic.main.navigation_header_layout.view.*
import uz.bdmgroup.barakasavdo.MainViewModel
import uz.bdmgroup.barakasavdo.R
import uz.bdmgroup.barakasavdo.Utils.LocaleManager
import uz.bdmgroup.barakasavdo.screen.cart.CartFragment
import uz.bdmgroup.barakasavdo.screen.changelanguage.ChangeLanguageFragment
import uz.bdmgroup.barakasavdo.screen.favorite.FavoriteFragment
import uz.bdmgroup.barakasavdo.screen.home.HomeFragment
import uz.bdmgroup.barakasavdo.screen.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    val homeFragment = HomeFragment.newInstance()
    val favoriteFragment = FavoriteFragment.newInstance()
    val cartFragment = CartFragment.newInstance()
    val profileFragment = ProfileFragment.newInstance()
    var activeFragment: Fragment = homeFragment

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel= MainViewModel()
       viewModel.topProductData.observe(this, Observer {
           viewModel.insertAllProducts2DB(it)
           homeFragment.loadData()
       })
        viewModel.categoriessData.observe(this, Observer {
            viewModel.insertAllCategories2DB(it)
            homeFragment.loadData()
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        //nav menu itemlari bosilgandagi buyruqlar
        navigationmenu.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profiledriver -> {
                    Toast.makeText(this, "Profil clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.languagedriver -> {
                    val fragment = ChangeLanguageFragment.newInstance()
                    fragment.show(supportFragmentManager, fragment.tag)
                }
                R.id.logout -> {
                    finish()
                    startActivity(Intent(this, SplashActivity::class.java))
                }
                R.id.settingdriver -> {
                    Toast.makeText(this, "setting clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.aboutdriver -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                }
                R.id.shareprogramm -> {
                    //share window yaratish
                    val intent=Intent(Intent.ACTION_SEND)
                    intent.type="text/plain"
                    //intent.putExtra(Intent.EXTRA_SUBJECT, "hgjyfcytv: ")
                    intent.putExtra(Intent.EXTRA_TEXT, "Mening ilovamni sinab ko'ring va baho bering : t.me//jamshid_muxtoraliyev/ ")
                    startActivity(intent)
                }
            }
            true
        }
        //comment qoshildi

        //dravermenu ni ochish
        imgMenu.setOnClickListener {
            draverLayout.openDrawer(GravityCompat.START)
        }
        //ftycrycfr

        navigationmenu.getHeaderView(0).closemenudriver.setOnClickListener {
            draverLayout.closeDrawers()
        }
        //fghjk



        supportFragmentManager.beginTransaction()
            .add(R.id.flcontainer, homeFragment, homeFragment.tag).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flcontainer, favoriteFragment, favoriteFragment.tag).hide(favoriteFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flcontainer, cartFragment, cartFragment.tag).hide(cartFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flcontainer, profileFragment, profileFragment.tag).hide(profileFragment)
            .commit()

        supportFragmentManager.beginTransaction().show(activeFragment).commit()

        buttonnavigationview.setOnNavigationItemSelectedListener {

            if (it.itemId == R.id.action_home) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment)
                    .commit()
                activeFragment = homeFragment
            } else if (it.itemId == R.id.action_favorite) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(favoriteFragment).commit()
                activeFragment = favoriteFragment
            } else if (it.itemId == R.id.action_cart) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(cartFragment)
                    .commit()
                activeFragment = cartFragment
            } else if (it.itemId == R.id.action_profile) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(profileFragment)
                    .commit()
                activeFragment = profileFragment
            }

            return@setOnNavigationItemSelectedListener true
        }
        loadData()
    }

    fun loadData(){
        viewModel.getTopProduct()
        viewModel.getCategories()
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))

    }
}