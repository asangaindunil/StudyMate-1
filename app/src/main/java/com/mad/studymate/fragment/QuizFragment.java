package com.mad.studymate.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mad.studymate.R;
import com.mad.studymate.activity.AddQuizActivity;
import com.mad.studymate.activity.AnswerQuizActivity;
import com.mad.studymate.cardview.adapter.QuizCardAdapter;
import com.mad.studymate.cardview.model.Quiz;
import com.mad.studymate.db.QuizDbHelper;
import com.mad.studymate.db.StudyMateContractor;

import java.util.ArrayList;
import java.util.List;

public class QuizFragment extends Fragment {

    private Menu menu;
    private SearchView mSearchView;

    //add notes fab
    FloatingActionButton fab;

    //database helper to get each and every quiz
    QuizDbHelper mDbHelper;
    private RecyclerView mRecyclerView;
    private QuizCardAdapter mAdapter;
    private List<Quiz> quizList;
    private OnFragmentInteractionListener mListener;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        //getting the recyclerview from xml
        mRecyclerView = view.findViewById(R.id.idQuizRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Populate the groups
        quizList = new ArrayList<>();
        quizList.add(new Quiz("Programming basics","programming", "Multiple Answer", 27, 0, 0));
        quizList.add(new Quiz("Software Engineering","SE", "Single Answer", 100, 1, 100));
        quizList.add(new Quiz("Hacking Advance","hacking", "True or False", 5, 2, 34));

        //get quiz from database
        Cursor cursor = retrieveAllQuizes();
        while (cursor.moveToNext()) {
            quizList.add(new Quiz(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getDouble(6)));
        }
        cursor.close();

        //set adapter to recyclerview
        mAdapter = new QuizCardAdapter(quizList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        setHasOptionsMenu(true);

        //card clicked event with sending necessary data to the answering activity.
        mAdapter.setOnItemClickListener(new QuizCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Quiz quiz = quizList.get(position);
                Intent intent = new Intent(getActivity(), AnswerQuizActivity.class);
                intent.putExtra("title", quiz.getTitle());
                intent.putExtra("quizTag", quiz.getQuizTag());
                intent.putExtra("quizType", quiz.getQuizType());
                intent.putExtra("questionCount", quiz.getQuestionCount());
                intent.putExtra("quizScores", quiz.getScoresOfQuiz());
                intent.putExtra("timesTaken", quiz.getTimesTaken());
                startActivity(intent);
            }
        });


        //fab click listner to open add quiz activity
        fab = view.findViewById(R.id.idQuizAddFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddQuizActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private Cursor retrieveAllQuizes() {
        //get quiz from database
        mDbHelper = new QuizDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                StudyMateContractor.QuizEntry.COLUMN_NAME_TITLE,
                StudyMateContractor.QuizEntry.COLUMN_NAME_TAG,
                StudyMateContractor.QuizEntry.COLUMN_NAME_TYPE,
                StudyMateContractor.QuizEntry.COLUMN_NAME_QUESTIONS_COUNT,
                StudyMateContractor.QuizEntry.COLUMN_NAME_ATTEMPT_COUNT,
                StudyMateContractor.QuizEntry.COLUMN_NAME_QUIZ_SCORES
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                StudyMateContractor.QuizEntry._ID + " DESC";

        Cursor cursor = db.query(
                StudyMateContractor.QuizEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        return cursor;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu,inflater);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return true;
            }
        });
    }
}
