package kr.go.seoul.seoultrail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by 지호 on 2017-09-29.
 */

public class CommunityPractice extends BaseActivity {
    private RecyclerView myRecyclerView;
    private TestAdapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_activity);
        myRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        myRecyclerView.setHasFixedSize(true);

        // 레이아웃 매니저 설정
        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);

        myAdapter = new TestAdapter();
        myRecyclerView.setAdapter(myAdapter);
        addTestData();
    }

    private void addTestData() {

        for(int i=0; i < 30; i++) {
            Student student = new Student();
            student.setName("리사이클러뷰를 공부하는데 지금 너무 피곤해서 토가 나올 지경이다. 그렇지만" +
                    "나는 해내고 말것이다. 나는 반드시 해낸다. 나는 성공해서 엄마 호강시켜줘야되고 잘먹고잘살아야되고 불쌍한 사람도 많이 도와줘야된다." + i);
            student.setScore(String.valueOf(i) + "분전");

            myAdapter.add(student);
        }
    }
}
