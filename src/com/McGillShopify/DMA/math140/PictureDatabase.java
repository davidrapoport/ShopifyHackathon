package com.McGillShopify.DMA.math140;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PictureDatabase extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "PictureDatabase";
	private static final String TABLE_NAME_RATPOLY = "RATIONALPOLYLIMITS";
	private static final String TABLE_NAME_POLYDERIV = "POLYNOMIALDERIVATIVES";
	private static final int versionNumb = 1;

	// private static final String[] typesOfProblems =
	// {"RationalPolyLimits"};//add more as needed

	public PictureDatabase(Context context) {
		super(context, DATABASE_NAME, null, versionNumb);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		String toCreateRatPoly = "CREATE TABLE " + TABLE_NAME_RATPOLY
				+ "( imageBitmap TEXT, seen TEXT, answer TEXT)";
		String toCreatePolyDeriv = "CREATE TABLE " + TABLE_NAME_POLYDERIV
				+ "( imageBitmap TEXT, seen TEXT, answer TEXT)";
		arg0.execSQL(toCreateRatPoly);
		arg0.execSQL(toCreatePolyDeriv);
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RATPOLY);
		arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_POLYDERIV);
		onCreate(arg0);

	}

	public void populate(Context context) throws FileNotFoundException, IOException {
		AssetManager am = context.getAssets();
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.beginTransaction();
		try {
			int check =1;
			for (String types : am.list("")) {
				if (am.list(types).length > 2 && types.indexOf("webkit") < 0) {
					// System.out.println(types);
					InputStream in = am.open(types + "/mathematica_"
							+ types.toLowerCase() + "_answers.txt");
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in));
					int counter = 0;
					//Arrays.sort(am.list(types), 0, am.list(types).length);
					Log.w("Number of iterations", ""+am.list(types).length);
					for (; counter < am.list(types).length - 1; counter++) { // minus one for answers.txt
						
						String answer = br.readLine();
						//int neo = Arrays.binarySearch(am.list(types),
								//"mathematica_" + types.toLowerCase() + "_"
							//			+ counter + ".png");
						// System.out.println("mathematica_"+types.toLowerCase()+"_image"+counter+".png");
						//if (neo >= -1) {
							ContentValues vals = new ContentValues();
							//ByteArrayOutputStream baos = new ByteArrayOutputStream();
						/*	Bitmap bm = BitmapFactory.decodeStream(am
									.open(types + "/mathematica_"
											+ types.toLowerCase() + "_"
											+ counter + ".png"));

							String imageBM = Methods.BitMapToString(bm); */
							// vals.put("FolderName", types.toUpperCase());
							vals.put("imageBitmap", types + "/mathematica_"+ types.toLowerCase() + "_"+ counter + ".png");
							vals.put("seen", "false");
							vals.put("answer", answer);
						    db.insert(types.toUpperCase(), null, vals);
							
						//} else {
						//	Log.w("Picture error", "Could not find picture "
								//	+ types + counter);
//
						//}

					}
					in.close();
					br.close();
				}
			}
			db.setTransactionSuccessful();
		} catch (SQLiteException e) {
		} finally {
			db.endTransaction();
			db.close();
			
		}
	}

	public String getTest() {
		SQLiteDatabase db = getReadableDatabase();
		// check if empty
		Cursor curs = db.query(TABLE_NAME_RATPOLY,
				new String[] { "imageBitmap" }, "answer='1/4'", null, null,
				null, null);
		if (curs != null) {
			curs.moveToFirst();

			return curs.getString(0);
		}
		return null;

	}

	public String[] getUnseenRandom(Context context, String typeOfProblem) {
		// Throw an exception if no more are left
		SQLiteDatabase db = getReadableDatabase();
		// Cursor test = db.query(typeOfProblem.toUpperCase(), new String[]
		// {"imageBitmap","seen","answer"}, null, null, null, null, null);
		// Log.w("cursor test if empty", "" + test.moveToLast());
		// Log.w("cursor test for last row answer", test.getString(2));
		// test.close();
		Cursor curs = db.query(typeOfProblem.toUpperCase(), new String[] {
				"imageBitmap", "seen", "answer" }, "seen='false'", null, null,
				null, null);

		String[] picAndAnswers = new String[2];
		if (curs != null) {
			curs.moveToNext();
			picAndAnswers[0] = curs.getString(0);
			picAndAnswers[1] = curs.getString(2);
			curs.close();
			ContentValues vals = new ContentValues();
			vals.put("imageBitmap", picAndAnswers[0]);
			vals.put("seen", "'true'");
			vals.put("answer", picAndAnswers[1]);
			int affected = db.update(typeOfProblem.toUpperCase(), vals,
					"imageBitmap='" + picAndAnswers[0] + "'", null);
			if (affected != 1)
				Log.w("Update Error", "" + affected
						+ " rows affected by update on call to getUnseenRandom");

			return picAndAnswers;
		} else {
			System.out.println("null cursor");
		}
		return picAndAnswers;
	}

}
