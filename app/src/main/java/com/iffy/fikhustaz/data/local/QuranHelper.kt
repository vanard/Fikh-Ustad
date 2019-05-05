package com.iffy.fikhustaz.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.iffy.fikhustaz.data.model.quran.Quran
import com.iffy.fikhustaz.data.model.quran.Surat
import org.jetbrains.anko.db.*

class QuranHelper (ctx: Context) : ManagedSQLiteOpenHelper(ctx, "alq.db", null, 1){

    companion object {
        private var instance: QuranHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) : QuranHelper{
            if (instance == null){
                instance = QuranHelper(ctx.applicationContext)
            }
            return instance as QuranHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(Quran.TABLE_QURAN, true,
            Quran.QURAN_ARTI to TEXT,
            Quran.QURAN_ASMA to TEXT,
            Quran.QURAN_AUDIO to TEXT,
            Quran.QURAN_AYAT to TEXT,
            Quran.QURAN_KET to TEXT,
            Quran.QURAN_NAMA to TEXT,
            Quran.QURAN_NOMOR to TEXT,
            Quran.QURAN_RUKUK to TEXT,
            Quran.QURAN_TIPE to TEXT,
            Quran.QURAN_URUT to TEXT
        )

        db?.createTable(Surat.TABLE_SURAH, true,
            Surat.ID to TEXT,
            Surat.SURAH_AR to TEXT,
            Surat.SURAH_ID to TEXT,
            Surat.SURAH_NOMOR to TEXT,
            Surat.SURAH_TR to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Quran.TABLE_QURAN, true)
        db?.dropTable(Surat.TABLE_SURAH, true)
    }

}

val Context.db : QuranHelper
    get() = QuranHelper.getInstance(applicationContext)