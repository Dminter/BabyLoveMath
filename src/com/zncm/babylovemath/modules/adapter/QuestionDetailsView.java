package com.zncm.babylovemath.modules.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zncm.babylovemath.R;
import com.zncm.utils.sp.StatedPerference;

public class QuestionDetailsView extends RelativeLayout {
    private LayoutInflater mInflater;
    private LinearLayout llView;
    private TextView tvPage;
    private TextView tvContent;
    private TextView tvAnswer;
    private ImageView ivPre;
    private ImageView ivNext;
    private RadioGroup rbGroup;
    private RadioButton rbA;
    private RadioButton rbB;
    private RadioButton rbC;
    private RadioButton rbD;

    public QuestionDetailsView(Context context) {
        this(context, null);
    }

    public QuestionDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        llView = (LinearLayout) mInflater.inflate(R.layout.view_question_details, null);
        llView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(llView);
        tvPage = (TextView) llView.findViewById(R.id.tvPage);
        tvContent = (TextView) llView.findViewById(R.id.tvContent);
        tvAnswer = (TextView) llView.findViewById(R.id.tvAnswer);
        ivPre = (ImageView) llView.findViewById(R.id.ivPre);
        ivNext = (ImageView) llView.findViewById(R.id.ivNext);


        rbGroup = (RadioGroup) llView.findViewById(R.id.rbGroup);
        rbA = (RadioButton) llView.findViewById(R.id.rbA);
        rbB = (RadioButton) llView.findViewById(R.id.rbB);
        rbC = (RadioButton) llView.findViewById(R.id.rbC);
        rbD = (RadioButton) llView.findViewById(R.id.rbD);

        tvPage.setTextSize(StatedPerference.getFontSize() - 5);
        tvContent.setTextSize(StatedPerference.getFontSize() + 10);
        tvAnswer.setTextSize(StatedPerference.getFontSize());
        rbA.setTextSize(StatedPerference.getFontSize());
        rbB.setTextSize(StatedPerference.getFontSize());
        rbC.setTextSize(StatedPerference.getFontSize());
        rbD.setTextSize(StatedPerference.getFontSize());


    }

    public TextView getTvPage() {
        return tvPage;
    }

    public TextView getTvContent() {
        return tvContent;
    }

    public RadioButton getRbA() {
        return rbA;
    }

    public RadioButton getRbB() {
        return rbB;
    }

    public RadioButton getRbC() {
        return rbC;
    }

    public RadioButton getRbD() {
        return rbD;
    }

    public RadioGroup getRbGroup() {
        return rbGroup;
    }

    public TextView getTvAnswer() {
        return tvAnswer;
    }

    public ImageView getIvPre() {
        return ivPre;
    }

    public ImageView getIvNext() {
        return ivNext;
    }
}
