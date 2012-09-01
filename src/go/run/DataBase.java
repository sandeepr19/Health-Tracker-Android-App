package go.run;
public final class DataBase {


	public static final String user_TableName = "user_Table";
	public static final String exercise_TableName = "exercise_Table";
	public static final String defaulthours_TableName = "defaulthours_Table";
	public static final class DDL {
		private static final StringBuilder user_Table = new StringBuilder(
				256);
		private static final StringBuilder exercise_TABLE = new StringBuilder(256);
		private static final StringBuilder defaulthours_TABLE = new StringBuilder(256);

		private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

		// initialize the queries on class load
		// will happen only once for the time class is loaded in JVM
		static {
			
			
			user_Table.append("CREATE  TABLE ");
			user_Table.append(user_TableName);
			user_Table.append("( age INT NOT NULL)");
			//user_Table.append("sex varchar2(10) NOT NULL)");

			defaulthours_TABLE.append("CREATE  TABLE ");
			defaulthours_TABLE.append(defaulthours_TableName);
			defaulthours_TABLE.append("( startage INT NOT NULL,endage INT NOT NULL,hours INT NOT NULL,percentage varchar2(10) )");
			
			exercise_TABLE.append("CREATE TABLE ");
			exercise_TABLE.append(exercise_TableName);
			exercise_TABLE.append("(`id` INTEGER PRIMARY KEY ,`startime` TIMESTAMP NOT NULL ,`endtime` TIMESTAMP NOT NULL,`duration` INT  );");
		}

		public static String dropTableQuery(final String tableName) {
			return DROP_TABLE + tableName;

		}

		
		public static final String getTableCreationQuery(final String tableName) {
			if (tableName.equalsIgnoreCase(user_TableName)) {
				return user_Table.toString();
			} else if (tableName.equalsIgnoreCase(exercise_TableName)) {
				return exercise_TABLE.toString();
			}
			else if (tableName.equalsIgnoreCase(defaulthours_TableName)) {
				return defaulthours_TABLE.toString();
			} 
			else {
				return new String(new char[0]);
			}
		}
	}
}