package com.McGillShopify.DMA.math140;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PictureDatabase extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "PictureDatabase";
	private static final String TABLE_NAME = "PictureTable";
	private static final int versionNumb = 1;


	public PictureDatabase(Context context) {
		super(context, DATABASE_NAME, null, versionNumb);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		String toCreate = "CREATE DATABASE " + TABLE_NAME +"(FolderName Text, imageBitmap Text, seen Boolean, answer Text)";
		arg0.execSQL(toCreate);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		arg0.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(arg0);
		
	}
	public String test()throws FileNotFoundException, IOException{
		
		//File k = new File(R.drawable.answers);
		//File s = new File(R.drawable.MathematicaFiles.RationalPolyLimits.answers.txt);
		//BufferedReader fr = new BufferedReader(new FileReader(R.drawable.answers));
		//String l = fr.readLine();
		//fr.close();
		String l = R.drawable.answers;
		return l;
	}

}
