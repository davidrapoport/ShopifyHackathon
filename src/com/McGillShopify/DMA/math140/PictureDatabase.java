package com.McGillShopify.DMA.math140;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class PictureDatabase extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "PictureDatabase";
	private static final String TABLE_NAME = "PictureTable";
	private static final int versionNumb = 1;
	//private static final String[] typesOfProblems = {"RationalPolyLimits"};//add more as needed
	


	public PictureDatabase(Context context) {
		super(context, DATABASE_NAME, null, versionNumb);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		String toCreate = "CREATE TABLE " + TABLE_NAME +"(FolderName TEXT, imageBitmap TEXT, seen BOOLEAN, answer TEXT)";
		arg0.execSQL(toCreate);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		arg0.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(arg0);
		
	}
	public void populate(Context context)throws FileNotFoundException, IOException{
		//more efficient,sort?
		AssetManager am = context.getAssets();
		for(String types : am.list("")){
			if(am.list(types).length>2){
				//System.out.println(types);
				InputStream in = am.open(types+"/mathematica_"+types.toLowerCase()+"_answers.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				int counter=0;
				Arrays.sort(am.list(types), 0, am.list(types).length);
				SQLiteDatabase db = this.getWritableDatabase();
				for(;counter<am.list(types).length-1;counter++){ //minus one for answers.txt
					String answer = br.readLine();
					int neo =Arrays.binarySearch(am.list(types), "mathematica_"+types.toLowerCase()+"_"+counter+".png");
					//System.out.println("mathematica_"+types.toLowerCase()+"_image"+counter+".png");
					if(neo >=-1){
						ContentValues vals = new ContentValues();
						ByteArrayOutputStream baos=new  ByteArrayOutputStream();
						Bitmap bm = BitmapFactory.decodeStream(am.open(types+"/mathematica_"+types.toLowerCase()+"_"+counter+".png"));
						bm.compress(Bitmap.CompressFormat.PNG,100, baos);
				        byte [] b=baos.toByteArray();
				        String temp=Base64.encodeToString(b, Base64.DEFAULT);
				          
						vals.put("FolderName", types);
						vals.put("imageBitmap", temp);
						vals.put("seen", false);
						vals.put("answer",answer);
						db.insert(TABLE_NAME,null,vals);
					}
					else{
						Log.w("Picture error","Could not find picture "+types+counter);
						
					}
					
				}
			}
			}
			
		}
	public String getTest(){
		SQLiteDatabase db = getReadableDatabase();
		//check if empty
		Cursor curs = db.query(TABLE_NAME, new String[] {"imageBitmap"}, "answer='1/4'", null, null, null, null);
		if(curs !=null){
			curs.moveToFirst();
			System.out.println(curs.getCount());
			return curs.getString(0);
		}
		return null;
		
	}
	}


