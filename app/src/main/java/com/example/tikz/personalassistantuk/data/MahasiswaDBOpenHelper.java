package com.example.tikz.personalassistantuk.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.print.PrinterId;

public class MahasiswaDBOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "personalAssistant.db";
    //TABLE DATA MAHASISWA
    public static final String TABLE_NAME = "mahasiswa";
    public static final String COL_ID = "id";
    public static final String COL_FNAME = "fname";
    public static final String COL_LNAME = "lname";
    public static final String COL_NIM = "nim";
    public static final String COL_PASS = "password";

    //TABLE SCHEDULE
    public static final String TABLE_SCHEDULE = "schedule";
    public static final String COL_SUBJECT_ID = "_id";
    public static final String COL_SUB_ID = "subject_code";
    public static final String COL_SUBJECT_NAME = "subject_name";
    public static final String COL_SUBJECT_PARALLEL = "subject_parallel";
    public static final String COL_SUBJECT_TIME = "subject_date";
    public static final String COL_SUBJECT_TIMER = "subject_timer";
    public static final String COL_SUBJECT_DAY = "subject_day";
    public static final String COL_SUBJECT_ROOM = "subject_classroom";
    public static final String COL_SUBJECT_TEACHER = "subject_teacher";
    public static final String COL_SUBJECT_ACTIVE = "active";

    //TABLE EVENT
    public static final String TABLE_EVENT = "event";
    public static final String COL_EVENT_ID = "_id";
    public static final String COL_EVENT_NAME = "event_title";
    public static final String COL_EVENT_INFO = "event_info";
    public static final String COL_EVENT_DATE = "event_date";
    public static final String COL_EVENT_TIME = "event_time";
    public static final String COL_EVENT_TIMER = "event_timer";
    public static final String COL_EVENT_ACTIVE = "active";

    //TABLE ASSIGNMENT & EXAM
    public static final String TABLE_NAME_ASSEXAM = "ass_exam";
    public static final String COL_ASSEXAM_ID = "_id";
    public static final String COL_ASSEXAM_TITLE = "assexam_title";
    public static final String COL_ASSEXAM_SUB = "subject_name";
    public static final String COL_ASSEXAM_DATE = "assexam_date";
    public static final String COL_ASSEXAM_TIME = "assexam_time";
    public static final String COL_ASSEXAM_INFO = "assexam_info";
    public static final String COL_ASSEXAM_TYPE = "type";
    public static final String COL_ASSEXAM_ACTIVE = "active";

    //TABLE LIST SUBJECT
    public static final String TABLE_SUBJECT = "subject";
    public static final String COL_ID_SUBJECT = "_id";
    public static final String COL_SUBJECT_CODE = "code";
    public static final String COL_SUBJECT_TITLE = "subject_name";
    public static final String COL_SUBJECT_TITLE2 = "subject_name2";

    private static String TBL_CREATE_MAHASISWA = "CREATE TABLE " + TABLE_NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_FNAME + " TEXT, "
            + COL_LNAME + " TEXT, "
            + COL_NIM + " TEXT, "
            + COL_PASS + " TEXT " +");";

    private static String TBL_CREATE_SCHEDULE = "CREATE TABLE " + TABLE_SCHEDULE + " ("
            + COL_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_SUB_ID + " TEXT, "
            + COL_SUBJECT_NAME + " TEXT, "
            + COL_SUBJECT_PARALLEL + " TEXT, "
            + COL_SUBJECT_TIME + " TEXT, "
            + COL_SUBJECT_TIMER + " TEXT, "
            + COL_SUBJECT_DAY + " TEXT, "
            + COL_SUBJECT_ROOM + " TEXT, "
            + COL_SUBJECT_TEACHER + " TEXT, "
            + COL_SUBJECT_ACTIVE + " TEXT " + ");";

    private static String TBL_CREATE_EVENT = "CREATE TABLE " + TABLE_EVENT + " ("
            + COL_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_EVENT_NAME + " TEXT, "
            + COL_EVENT_INFO + " TEXT, "
            + COL_EVENT_DATE + " TEXT, "
            + COL_EVENT_TIME + " TEXT, "
            + COL_EVENT_TIMER + " TEXT, "
            + COL_EVENT_ACTIVE + " TEXT " + ");";

    private static String TBL_CREATE_ASSEXAM = "CREATE TABLE " + TABLE_NAME_ASSEXAM + " ("
            + COL_ASSEXAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_ASSEXAM_TITLE + " TEXT, "
            + COL_ASSEXAM_SUB + " TEXT, "
            + COL_ASSEXAM_DATE + " TEXT, "
            + COL_ASSEXAM_TIME + " TEXT, "
            + COL_ASSEXAM_INFO + " TEXT, "
            + COL_ASSEXAM_TYPE + " TEXT, "
            + COL_ASSEXAM_ACTIVE + " TEXT " + ");";

    private static String TBL_CREATE_SUBJECT = "CREATE TABLE " + TABLE_SUBJECT + " ("
            + COL_ID_SUBJECT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_SUBJECT_CODE + " TEXT, "
            + COL_SUBJECT_TITLE + " TEXT, "
            + COL_SUBJECT_TITLE2 + " TEXT " + ");";

    private static final int DATABASE_VERSION=1;

    //Constructor
    public MahasiswaDBOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TBL_CREATE_MAHASISWA);
        db.execSQL(TBL_CREATE_SCHEDULE);
        db.execSQL(TBL_CREATE_EVENT);
        db.execSQL(TBL_CREATE_ASSEXAM);
        db.execSQL(TBL_CREATE_SUBJECT);

        //Insert table subject
        //Prerequisite
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('FENG000', 'Bahasa Inggris Dasar', 'Foundation of English')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COPS000', 'Keterampilan Komputer Dasar', 'Basic Computer Skill')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('WEDU001', 'Pendidikan Keterampilan', 'Work Education')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('MATH000', 'Matematika', 'Mathematics')");
        //Semester 1
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COPS121', 'Pengantar Komputer', 'Introduction to Computer')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('AGMA181', 'Hidup dan Ajaran Yesus I', 'Life and Teaching of Jesus I')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('SENG191', 'Pengrograman Komputer I', 'Computer Programming I')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('INGU121', 'Bahasa Inggris I', 'General English')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('MATH172', 'Kalkulus I', 'Calculus I')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('MATH171', 'Linear Aljabar dan Matrik', 'Linear Algebra and Matrix')");
        //Semester 2
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('MATH173', 'Matematika Diskrit', 'Discrete Mathematics')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('MATH174', 'Kalkulus II', 'Calculus II')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('SENG192', 'Pemrograman Komputer II', 'Computer Programming II')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COHD111', 'Perancangan Digital dan Logika', 'Digital and Logic Design')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('INGU122', 'Bahasa Inggris II', 'General English II')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('AGMA182', 'Hidup dan Ajaran Yesus II', 'Life and Teaching of Jesus')");
        //Semester 3
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('MATH271', 'Statistik dan Probabilitas', 'Statistics and Probability')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COPS225', 'Algoritma dan Struktur Data', 'Algorithm and Data Structure')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('AGMA283', 'Prinsip-Prinsip Alkitab I', 'Biblical Principles I')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('INGU223', 'Bahasa Inggris III', 'General English III')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COHD211', 'Organisasi dan Arsitektur Komputer', 'Computer Organization and Architecture')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('PEND131', 'Filsafat Pendidikan Kristen', 'Philosophy of Christian Education')");
        //Semester 4
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('CNET201', 'Jaringan Komputer I', 'Computer Network I')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('AGMA284', 'Prinsip-Prinsip Alkitab II', 'Biblical Principles II')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('INGU224', 'Bahasa Inggris IV', 'General English IV')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('SENG292', 'Bahasa Rakitan', 'Assembly Language')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('SENG291', 'Pemrograman Berorientasi Obyek', 'Object Oriented Programming')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('PPKN101', 'Pendidikan Kewarganegaraan', 'Indonesian Civics')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('IFSY261', 'Ilmu Manajemen', 'Management Science')");
        //Semester 5
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COPS222', 'Teori Bahasa dan Automata', 'Language and Automata Theory')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('IFSY361', 'Analisis dan Perancangan Sistem', 'System Analysis and Design')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('SENG391', 'Pemrograman Visual', 'Visual Programming')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('CNET301', 'Jaringan Komputer II', 'Computer Network II')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COPS321', 'Konsep-Konsep Sistem Operasi', 'Operating System Concepts')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('DABS251', 'Pengantar Database', 'Introduction to Database')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('AGMA385', 'Nubuatan Alkitab I', 'Biblical Prophecies I')");
        //Semester 6
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('SENG394', 'Pemrograman Framework', 'Framework Programming')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('RSCH382', 'Metodologi Penelitian', 'Research Method')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('AGMA386', 'Nubuatan Alkitab II', 'Biblical Prophecies II')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('SENG393', 'Rekayasa Perangkat Lunak', 'Software Engineering')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COPS223', 'Sistem Berkas', 'File Structure')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COPS323', 'Teori Kompilasi', 'Compiler Theory')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('DABS252', 'Sistem Manajemen Basisdata', 'Database Management System')");
        //Semester 7
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COPS421', 'Kecerdasan Buatan', 'Artificial Intelligence')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('SENG351', 'Pemrograman Web', 'Web Programming')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('RSCH383', 'Menulis Ilmiah', 'Research Writing')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('CSEC441', 'Etika Komputer', 'Computer Ethics')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COPS424', 'Grafik Komputer', 'Computer Graphics')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('RSCH481', 'Skripsi I', 'Research Project I')");
        //Semester 8
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COPS425', 'Pemrograman Sistem', 'System Programming')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('CSEC442', 'Keamanan Komputer', 'Computer Security')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('RSCH482', 'Skripsi II', 'Research Project II')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('IFSY465', 'Manajemen Proyek', 'Project Management')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COPS426', 'Pemrograman Multiplatform', 'Multiplatform Programming')");
        //Semester 9
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('BENG330', 'Komunikasi Bisnis Bahasa Inggris', 'Business English Communication')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('IFSY463', 'Knowledge Management', 'Knowledge Management')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('EGRC492', 'Persiapan TOEFL', 'TOEFL Preparation Class')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('ELAN491', 'Penerjemahan dan Komunikasi Internasional', 'Translation and International Communication')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('CSAR441', 'Perancangan Seni & Visual', 'Arts and Visual Design')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COMP482', 'Teknologi Komputasi Internet', 'Cloud Computing Technology')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('COPS472', 'Pengembangan Perangkat Bergerak', 'Mobile Device Development')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('EWRI343', 'Menulis III', 'Writing III')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('CSAR331', 'Perancangan Web', 'Web Design')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('BENG320', 'Korespondensi Bisnis Bahasa Inggris', 'Business English Correspondence')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('BENG310', 'Bahasa Inggris Bisnis Membaca dan Kosakata', 'Business English Reading and Vocabulary')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('DABS451', 'Data Mining', 'Data Mining')");
        db.execSQL("INSERT INTO " + TABLE_SUBJECT + "(code, subject_name, subject_name2 ) VALUES ('CSAR431', 'Multimedia', 'Multimedia')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME + TBL_CREATE_MAHASISWA);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME + TBL_CREATE_SCHEDULE);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME + TBL_CREATE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME + TBL_CREATE_ASSEXAM);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME + TBL_CREATE_SUBJECT);
        onCreate(db);
    }
}
