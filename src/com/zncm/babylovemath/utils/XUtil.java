package com.zncm.babylovemath.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.zncm.babylovemath.R;
import com.zncm.utils.sp.StatedPerference;

public class XUtil {

    public abstract static class TwoAlertDialogFragment extends SherlockDialogFragment {
        public int icon;
        public String title;
        public String positive;
        public String negative;

        public TwoAlertDialogFragment(String title) {
            this.icon = R.drawable.info;
            this.title = title;
            this.positive = "确定";
            this.negative = "取消";
        }

        public TwoAlertDialogFragment(int icon, String title) {
            this.icon = icon;
            this.title = title;
            this.positive = "确定";
            this.negative = "取消";
        }

        public TwoAlertDialogFragment(int icon, String title, String positive, String negative) {
            this.icon = icon;
            this.title = title;
            this.positive = positive;
            this.negative = negative;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            return new AlertDialog.Builder(getActivity()).setIcon(icon).setTitle(title)
                    .setPositiveButton(positive, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            doPositiveClick();
                        }
                    }).setNegativeButton(negative, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            doNegativeClick();
                        }
                    }).create();
        }

        public abstract void doPositiveClick();

        public abstract void doNegativeClick();

    }

    public abstract static class FontSizeAlertDialogFragment extends SherlockDialogFragment {
        public int icon;
        public String title;
        public String content;

        public FontSizeAlertDialogFragment(String title) {
            this.icon = R.drawable.info;
            this.title = title;
        }

        public FontSizeAlertDialogFragment(int icon, String title) {
            this.icon = icon;
            this.title = title;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.view_font_size, null);
            final TableRow big = (TableRow) view.findViewById(R.id.tablerow_font_big);
            final TableRow middle = (TableRow) view.findViewById(R.id.tablerow_font_middle);
            final TableRow small = (TableRow) view.findViewById(R.id.tablerow_font_small);

            big.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setFontSize(25f);
                    dismiss();
                    doClick();
                }
            });
            middle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setFontSize(20f);
                    dismiss();
                    doClick();
                }
            });
            small.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setFontSize(15f);
                    dismiss();
                    doClick();
                }
            });

            return new AlertDialog.Builder(getActivity()).setIcon(icon).setTitle(title).setView(view).create();

        }

        protected abstract void doClick();


    }

    public abstract static class DifficultyAlertDialogFragment extends SherlockDialogFragment {
        public int icon;
        public String title;
        public String content;

        public DifficultyAlertDialogFragment(String title) {
            this.icon = R.drawable.info;
            this.title = title;
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.view_difficulty, null);
            final TableRow tablerow_10 = (TableRow) view.findViewById(R.id.tablerow_10);
            final TableRow tablerow_20 = (TableRow) view.findViewById(R.id.tablerow_20);
            final TableRow tablerow_50 = (TableRow) view.findViewById(R.id.tablerow_50);
            final TableRow tablerow_100 = (TableRow) view.findViewById(R.id.tablerow_100);
            final TableRow tablerow_500 = (TableRow) view.findViewById(R.id.tablerow_500);
            final TableRow tablerow_1000 = (TableRow) view.findViewById(R.id.tablerow_1000);

            tablerow_10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setDifficulty(10);
                    dismiss();
                    doClick();
                }
            });
            tablerow_20.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setDifficulty(20);
                    dismiss();
                    doClick();
                }
            });
            tablerow_50.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setDifficulty(50);
                    dismiss();
                    doClick();
                }
            });
            tablerow_100.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setDifficulty(100);
                    dismiss();
                    doClick();
                }
            });
            tablerow_500.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setDifficulty(500);
                    dismiss();
                    doClick();
                }
            });
            tablerow_1000.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setDifficulty(1000);
                    dismiss();
                    doClick();
                }
            });


            return new AlertDialog.Builder(getActivity()).setIcon(icon).setTitle(title).setView(view).create();

        }

        protected abstract void doClick();


    }

    public abstract static class QNmberAlertDialogFragment extends SherlockDialogFragment {
        public int icon;
        public String title;
        public String content;

        public QNmberAlertDialogFragment(String title) {
            this.icon = R.drawable.info;
            this.title = title;
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.view_qnumber, null);
            final TableRow tablerow_5 = (TableRow) view.findViewById(R.id.tablerow_5);
            final TableRow tablerow_10 = (TableRow) view.findViewById(R.id.tablerow_10);
            final TableRow tablerow_15 = (TableRow) view.findViewById(R.id.tablerow_15);
            final TableRow tablerow_20 = (TableRow) view.findViewById(R.id.tablerow_20);
            final TableRow tablerow_25 = (TableRow) view.findViewById(R.id.tablerow_25);
            final TableRow tablerow_30 = (TableRow) view.findViewById(R.id.tablerow_30);

            tablerow_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setQNumber(5);
                    dismiss();
                    doClick();
                }
            });
            tablerow_10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setQNumber(10);
                    dismiss();
                    doClick();
                }
            });
            tablerow_15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setQNumber(15);
                    dismiss();
                    doClick();
                }
            });
            tablerow_20.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setQNumber(20);
                    dismiss();
                    doClick();
                }
            });
            tablerow_25.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setQNumber(25);
                    dismiss();
                    doClick();
                }
            });
            tablerow_30.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setQNumber(30);
                    dismiss();
                    doClick();
                }
            });
            return new AlertDialog.Builder(getActivity()).setIcon(icon).setTitle(title).setView(view).create();

        }

        protected abstract void doClick();


    }


}
