package go.run;

import java.sql.Timestamp;
import java.util.Calendar;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {
	private static final String TAG = DBAdapter.class.getSimpleName();
	private static DBAdapter dbadapter;
	private volatile boolean isOpen = false;
	private DataBaseHelper DBHelper;
	private SQLiteDatabase db;
	private Context mContext;
	public static int MONTH = 2;
	public static int WEEK =7;
	public static int DAY	=1;
	private DBAdapter(Context ctx) {
		this.mContext = ctx;
		DBHelper = new DataBaseHelper(ctx);
	}

	public static DBAdapter getInstance(Context ctx) {
		if (dbadapter == null)
			dbadapter = new DBAdapter(ctx);

        SharedPreferences pref = ((Activity)ctx).getPreferences(0);
//		if(pref.getBoolean("dumpedData", false)){
//			Editor editor = pref.edit().putBoolean("dumpedData",true);
//			editor.commit();
//			dbadapter.openDataBase();
//			dbadapter.insertdefault();
//			dbadapter.setTransactionSuccessful();
//			dbadapter.closeDataBase();
//		}
		return dbadapter;
	}

	DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		
		return this;
	}

	public void close() {
		if (isOpen)
			DBHelper.close();
		isOpen = false;
	}

	public void beginTransaction() {
		db.beginTransaction();
	}

	public void endTransaction() {
		db.endTransaction();
	}

	public void setTransactionSuccessful() {
		db.setTransactionSuccessful();
	}

	public void openDataBase() {
		if (!isOpen)
			open();
		isOpen = true;
	}

	public void closeDataBase() {
		if (isOpen) {
			close();
			isOpen = false;
		}
	}

	/**
	 * This method is used to execute the SQL Query
	 * 
	 * @param sql
	 *            - The SQL Query to execute
	 * @param selectionArgs
	 *            - The Selection Arguments
	 * @return - A Cursor object, which is positioned before the first entry.
	 */
	public Cursor executeSQL(String sql, String[] selectionArgs) {
		return db.rawQuery(sql, selectionArgs);
	}

	public void insert(String table, String[] selectionArgs,
			ContentValues values) {
		db.insert(table, null, values);
	}

	private static class DataBaseHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "asdf";
		private static final int DATABASE_VERSION = 5;

		DataBaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.beginTransaction();
			try {
				db.execSQL(DataBase.DDL
						.getTableCreationQuery(DataBase.user_TableName));
				
				Log.e(DataBase.user_TableName,DataBase.DDL
						.getTableCreationQuery(DataBase.user_TableName));
				Log.e(DataBase.exercise_TableName,DataBase.DDL
						.getTableCreationQuery(DataBase.exercise_TableName));
				Log.e(DataBase.defaulthours_TableName,DataBase.DDL
						.getTableCreationQuery(DataBase.defaulthours_TableName));
				db.execSQL(DataBase.DDL
						.getTableCreationQuery(DataBase.exercise_TableName));
				db.execSQL(DataBase.DDL
						.getTableCreationQuery(DataBase.defaulthours_TableName));
				
				insertdefault(db);
				db.setTransactionSuccessful();
			} finally {

				db.endTransaction();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			// Drop table and then recreate

			db.beginTransaction();
			try {
				db.execSQL(DataBase.DDL
						.dropTableQuery(DataBase.user_TableName));
				db.execSQL(DataBase.DDL.dropTableQuery(DataBase.exercise_TableName));
				db.execSQL(DataBase.DDL.dropTableQuery(DataBase.defaulthours_TableName));
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
			onCreate(db);
		}
	}

	public void insertuser(String age) {
		db.beginTransaction();
		try {
			ContentValues values = new ContentValues();
			values.put("age", age);
			//values.put("sex", sex);
			db.insert(DataBase.user_TableName, null, values);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}
	
	
	public static void insertdefault(SQLiteDatabase db) {
		
		db.beginTransaction();
		try {
			ContentValues values = new ContentValues();
			values.put("startage", 14);
			values.put("endage", 14);
			values.put("hours", 60);
			values.put("percentage", "21.3");
			//values.put("sex", sex);
			db.insert(DataBase.defaulthours_TableName, null, values);
			values = new ContentValues();
			values.put("startage", 15);
			values.put("endage", 15);
			values.put("hours", 60 );
			values.put("percentage", "19.3");
			//values.put("sex", sex);
			db.insert(DataBase.defaulthours_TableName, null, values);
			values = new ContentValues();
			values.put("startage", 16);
			values.put("endage", 16);
			values.put("hours", 60);
			values.put("percentage", "17");
			//values.put("sex", sex);
			db.insert(DataBase.defaulthours_TableName, null, values);
			values = new ContentValues();
			values.put("startage", 17);
			values.put("endage", 17);
			values.put("hours", 60);
			values.put("percentage", "15.3");
			//values.put("sex", sex);
			db.insert(DataBase.defaulthours_TableName, null, values);
			values = new ContentValues();
			values.put("startage", 18);
			values.put("endage", 24);
			values.put("hours", 150);
			values.put("percentage", "57.2");
			//values.put("sex", sex);
			db.insert(DataBase.defaulthours_TableName, null, values);
			values = new ContentValues();
			values.put("startage", 25);
			values.put("endage", 44);
			values.put("hours", 150);
			values.put("percentage", "52.5");
			//values.put("sex", sex);
			db.insert(DataBase.defaulthours_TableName, null, values);
			values = new ContentValues();
			values.put("startage", 45);
			values.put("endage", 54);
			values.put("hours", 150);
			values.put("percentage", "47.6");
			//values.put("sex", sex);
			db.insert(DataBase.defaulthours_TableName, null, values);
			values = new ContentValues();
			values.put("startage", 55);
			values.put("endage", 64);
			values.put("hours", 150);
			values.put("percentage", "42.1");
			//values.put("sex", sex);
			db.insert(DataBase.defaulthours_TableName, null, values);
			values = new ContentValues();
			values.put("startage", 65);
			values.put("endage", 74);
			values.put("hours", 150);
			values.put("percentage", "26.8");
			//values.put("sex", sex);
			db.insert(DataBase.defaulthours_TableName, null, values);
			values = new ContentValues();
			values.put("startage", 75);
			values.put("endage", 79);
			values.put("hours", 150);
			values.put("percentage", "26.8");
			//values.put("sex", sex);
			db.insert(DataBase.defaulthours_TableName, null, values);
			values = new ContentValues();
			values.put("startage", 85);
			values.put("endage", 120);
			values.put("hours", 150);
			values.put("percentage", "15.7");
			//values.put("sex", sex);
			db.insert(DataBase.defaulthours_TableName, null, values);
			
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		
	}
	public void insertexcerxisetime(String startime, String endtime, long l) {
		db.beginTransaction();
		try {
			ContentValues values = new ContentValues();
			values.put("startime", startime);
			values.put("endtime", endtime);
			values.put("duration", l);
			db.insert(DataBase.exercise_TableName, null, values);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}
	
	
	
	public Cursor getavgc(String start,String end) {
		StringBuilder SQL = new StringBuilder(500);
		SQL.append("Select sum(duration) from ");
		SQL.append(DataBase.exercise_TableName);
		SQL.append(" where startime >= ");
		SQL.append(" '" + start + "' ");
		SQL.append(" and endtime <= ");
		SQL.append(" '" + end + "' ");
		//SQL.append(" ;");
		return db.rawQuery(SQL.toString(), null);
	}

	public int getsum(Calendar start,int type) {
		
		Calendar end = null ;
		int division =1;
		if(type== MONTH)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.set(Calendar.MONTH, start.get(Calendar.MONTH));
			calendar.set(Calendar.YEAR, start.get(Calendar.YEAR));
			start = (Calendar) calendar.clone();
			end = (Calendar) start.clone();
			end.set(Calendar.MONTH, start.get(Calendar.MONTH)+1);
			// division= start.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
			
		if(type== WEEK)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.set(Calendar.WEEK_OF_YEAR, start.get(Calendar.WEEK_OF_YEAR));
			calendar.set(Calendar.YEAR, start.get(Calendar.YEAR));
			start = (Calendar) calendar.clone();
			end = (Calendar) start.clone();
			end.set(Calendar.WEEK_OF_YEAR, start.get(Calendar.WEEK_OF_YEAR)+1);
			//division =7;
		}
		
		if(type== DAY)
		{
			start.set(Calendar.HOUR, 0);
			start.set(Calendar.MINUTE,0);
			start.set(Calendar.SECOND, 0);
			start.set(Calendar.MILLISECOND,0);
			end = (Calendar) start.clone();
			end.set(Calendar.DATE, start.get(Calendar.DATE)+1);
			//division =1;
		}
			
		if (isOpen) {
			Cursor cur = getavgc((new  Timestamp(start.getTime().getTime())).toString() , (new  Timestamp(end.getTime().getTime())).toString() );
			if (cur != null) {
				try {
					cur.moveToFirst();
					if (!cur.isAfterLast()) {
						Log.e("avg for "  + type + " : " , " "+ cur.getInt(0) );
						return cur.getInt(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					cur.close();
				}

			}
		}
		return -1;
	}


	
	
	public int getrecoomondedhoursc(int age) {
			StringBuilder SQL = new StringBuilder(500);
			SQL.append("Select hours from ");
			SQL.append(DataBase.defaulthours_TableName);
			SQL.append(" where startage <= ");
			SQL.append(" '" + age + "' ");
			SQL.append(" and endage >= ");
			SQL.append(" '" + age + "' ");
			//SQL.append(" ;");
			
			Log.e("query ",SQL.toString());
			
			Cursor cur= db.rawQuery(SQL.toString(), null);
			Log.e("cur is nnull",""+ (cur==null));
			if (cur != null) {
				try {
					cur.moveToFirst();
					
					if (!cur.isAfterLast()) {
						
						return cur.getInt(0);
					}
					else
						Log.e("emapty ", "cursor");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					cur.close();
				}

			}
			return -1;
		}

	/**
	 * This method is used to insert the surevey Questions and Answers in the
	 * table
	 * 
	 * @param surveyQuestionId
	 *            - The Question ID to be saved
	 * @param questionText
	 *            - The Question Text to be saved
	 * @param surveyAnswerId
	 *            - The Answer ID to be saved
	 * @param surveyAnswerText
	 *            - The Answer text to be saved
	 */

	//	public void insertSurveyQnA(String questionId, String questionText,
	//			String answerId, String answerText) {
	//		db.beginTransaction();
	//		try {
	//			ContentValues values = new ContentValues();
	//			values.put("surveyQuestionId", questionId);
	//			values.put("surveyQuestionText", questionText);
	//			values.put("surveyAnswerId", answerId);
	//			values.put("surveyAnswerText", answerText);
	//			db.insert(DataBase.SURVEY_DATA, null, values);
	//			db.setTransactionSuccessful();
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		} finally {
	//			db.endTransaction();
	//		}
	//	}
	//
	//	public Cursor getSurveyQuestion() {
	//		StringBuilder SQL = new StringBuilder(500);
	//		SQL.append("Select surveyQuestionId,surveyQuestionText from ");
	//		SQL.append(DataBase.SURVEY_DATA);
	//		return db.rawQuery(SQL.toString(), null);
	//	}
	//
	//	public Cursor getSurveyAnswer(String quesID) {
	//		StringBuilder SQL = new StringBuilder(500);
	//		SQL.append("Select DISTINCT surveyAnswerId,surveyAnswerText from ");
	//		SQL.append(DataBase.SURVEY_DATA);
	//		SQL.append(" where surveyQuestionId = ");
	//		SQL.append(" '" + quesID + "' ");
	//		SQL.append(" ;");
	//		return db.rawQuery(SQL.toString(), null);
	//	}

	//	public ArrayList<SurveyQuestionsBean> getSurveyQuestionData() {
	//		ArrayList<SurveyQuestionsBean> questionList = new ArrayList<SurveyQuestionsBean>();
	//		if (isOpen) {
	//			Cursor cur = getSurveyQuestion();
	//			if (cur != null) {
	//				try {
	//					cur.moveToFirst();
	//					while (!cur.isAfterLast()) {
	//						SurveyQuestionsBean surveyquestions = new SurveyQuestionsBean();
	//						surveyquestions.setQuestionID(cur.getString(cur
	//								.getColumnIndex("surveyQuestionId")));
	//						surveyquestions.setQuestionTxt(cur.getString(cur
	//								.getColumnIndex("surveyQuestionText")));
	//						questionList.add(surveyquestions);
	//						cur.moveToNext();
	//					}
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				} finally {
	//					cur.close();
	//				}
	//
	//				return questionList;
	//			}
	//		}
	//		return null;
	//	}
	//
	//	public ArrayList<SurveyAnswerBean> getSurveyAnswerData(String quesID) {
	//		ArrayList<SurveyAnswerBean> answerList = new ArrayList<SurveyAnswerBean>();
	//		if (isOpen) {
	//			Cursor cur = getSurveyAnswer(quesID);
	//			if (cur != null) {
	//				try {
	//					cur.moveToFirst();
	//					while (!cur.isAfterLast()) {
	//						SurveyAnswerBean surveyAnswers = new SurveyAnswerBean();
	//						surveyAnswers.setAnswerID(cur.getString(cur
	//								.getColumnIndex("surveyAnswerId")));
	//						surveyAnswers.setAnswerTxt(cur.getString(cur
	//								.getColumnIndex("surveyAnswerText")));
	//						answerList.add(surveyAnswers);
	//						cur.moveToNext();
	//					}
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				} finally {
	//					cur.close();
	//				}
	//
	//				return answerList;
	//			}
	//		}
	//		return null;
	//	}

	//	public int getSurveyQuestionCount() {
	//		int count = 0;
	//
	//		if (isOpen) {
	//			Cursor cur = getSurveyQuestion();
	//			if (cur != null) {
	//				try {
	//					cur.moveToFirst();
	//					while (!cur.isAfterLast()) {
	//						if (cur.getString(cur
	//								.getColumnIndex("surveyQuestionId")) != null) {
	//							count++;
	//							cur.moveToNext();
	//						}
	//					}
	//
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				} finally {
	//					cur.close();
	//				}
	//			}
	//		}
	//		return count;
	//	}

		public void clearexcercise() {
		
			StringBuilder SQL = new StringBuilder(500);
			SQL.append("DELETE FROM ");
			SQL.append(DataBase.exercise_TableName);
			Log.e("excersice query", SQL.toString());
			db.beginTransaction();
			db.execSQL(SQL.toString());
			db.setTransactionSuccessful();
			db.endTransaction();
			
		}
	//	public void clearAllNotifications() {
	//		int recordCount = getSurveyQuestionCount();
	//		StringBuilder SQL = new StringBuilder(500);
	//		SQL.append("DELETE FROM ");
	//		SQL.append(DataBase.notification_TableName);
	//		db.beginTransaction();
	//		db.execSQL(SQL.toString());
	//		db.setTransactionSuccessful();
	//		db.endTransaction();
	//		clearEventsNotifications();
	//	}
	//	
	//	public void clearEventsNotifications() {
	//		StringBuilder SQL = new StringBuilder(500);
	//		SQL.append("DELETE FROM ");
	//		SQL.append(DataBase.notificationEvents_TableName);
	//		db.beginTransaction();
	//		db.execSQL(SQL.toString());
	//		db.setTransactionSuccessful();
	//		db.endTransaction();
	//		
	//	}
	//	
	//	public void clearSurveyQnA(String questionID) {
	//		int recordCount = getSurveyQuestionCount();
	//		StringBuilder SQL = new StringBuilder(500);
	//		SQL.append("DELETE FROM ");
	//		SQL.append(DataBase.SURVEY_DATA);
	//		SQL.append(" WHERE surveyQuestionId = \"");
	//		SQL.append(questionID + "\"");
	//		db.beginTransaction();
	//		db.execSQL(SQL.toString());
	//		db.setTransactionSuccessful();
	//		int newRecordCount = getSurveyQuestionCount();
	//		newRecordCount = recordCount - newRecordCount;
	//		db.endTransaction();
	//		
	//	}
}