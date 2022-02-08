package uz.bdmgroup.barakasavdo

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.orhanobut.hawk.Hawk
import uz.bdmgroup.barakasavdo.databaseDAO.AppDataBase

class App: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        //multidex uchun
        MultiDex.install(this)

        //havwk keshga saqlovchi kutubxona uchun
        Hawk.init(this).build()

        //ro0m uchun
        AppDataBase.initDataBase(this)
    }
}