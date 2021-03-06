package com.mad.studymate.db;

import android.provider.BaseColumns;

public class StudyMateContractor {
    private StudyMateContractor() {}

    //database config
    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "StudyMate.db";

    //note
    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TAG = "tags";
        public static final String COLUMN_NAME_PARAGRAPH_COUNT = "paragraphs_count";
        public static final String COLUMN_NAME_PARAGRAPH_1 = "paragraph_1";
        public static final String COLUMN_NAME_PARAGRAPH_2 = "paragraph_2";
        public static final String COLUMN_NAME_PARAGRAPH_3 = "paragraph_3";
        public static final String COLUMN_NAME_PARAGRAPH_4 = "paragraph_4";
        public static final String COLUMN_NAME_PARAGRAPH_5 = "paragraph_5";
    }

    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TIME_PERIOD = "time_period";
        public static final String COLUMN_NAME_PRIORITY_NO = "priority_no";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IS_DONE = "is_done";
    }

    public static class QuizEntry implements BaseColumns {
        public static final String TABLE_NAME = "quizes";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TAG = "tag";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_QUESTIONS_COUNT = "questions_count";
        public static final String COLUMN_NAME_ATTEMPT_COUNT = "attempt_count";
        public static final String COLUMN_NAME_QUIZ_SCORES = "quiz_scores";
    }

    public static class AttemtedQuizEntry implements BaseColumns {
        public static final String TABLE_NAME = "attempted_quizes";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TAG = "tag";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_QUESTIONS_COUNT = "questions_count";
        public static final String COLUMN_NAME_ATTEMPT_COUNT = "attempt_count";
        public static final String COLUMN_NAME_QUIZ_SCORES = "quiz_scores";
    }

    public static class SessionEntry implements BaseColumns {
        public static final String TABLE_NAME = "study_session";
        public static final String Col_1 = "Lesson_Name";
        public static final String Col_2 = "Description";
        public static final String Col_3 = "wFrom";
        public static final String Col_4 = "wTo";
        public static final String Col_5 = "Complete";
        public static final String Col_6 = "Day";

    }
}
