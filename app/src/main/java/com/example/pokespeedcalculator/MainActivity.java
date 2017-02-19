package com.example.pokespeedcalculator;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.function.ToIntFunction;


public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    private CheckBox myScarfCheckBox, yourScarfCheckBox, myWindCheckBox, yourWindCheckBox, myGypsumCheckBox, yourGypsumCheckBox;
    PokeAbility myPokemon = new PokeAbility();
    PokeAbility yourPokemon = new PokeAbility();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //変数を定義し，idと結び付け
        RadioGroup myPersonalityGroup = (RadioGroup)findViewById(R.id.myPersonalityGroup);
        RadioGroup yourPersonalityGroup = (RadioGroup)findViewById(R.id.yourPersonalityGroup);

        myScarfCheckBox = (CheckBox)findViewById(R.id.myScarf);
        yourScarfCheckBox = (CheckBox)findViewById(R.id.yourScarf);
        myWindCheckBox = (CheckBox)findViewById(R.id.myWind);
        yourWindCheckBox = (CheckBox)findViewById(R.id.yourWind);
        myGypsumCheckBox = (CheckBox)findViewById(R.id.myGypsum);
        yourGypsumCheckBox = (CheckBox)findViewById(R.id.yourGypsum);
        Button calculateButton = (Button)findViewById(R.id.calculateButton);
        Button deleteButton = (Button)findViewById(R.id.deleteButton);

        //radioButtonから性格補正の値を取得
        myPersonalityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.myPersonalityUp:
                        myPokemon.personalityValue = 1.1;
                        break;
                    case R.id.myPersonalityNot:
                        myPokemon.personalityValue = 1.0;
                        break;
                    case R.id.myPersonalityDown:
                        myPokemon.personalityValue = 0.9;
                        break;
                    default:
                        myPokemon.personalityValue = 0;
                        break;
                }
            }
        });
        yourPersonalityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.yourPersonalityUp:
                        yourPokemon.personalityValue = 1.1;
                        break;
                    case R.id.yourPersonalityNot:
                        yourPokemon.personalityValue = 1.0;
                        break;
                    case R.id.yourPersonalityDown:
                        yourPokemon.personalityValue = 0.9;
                        break;
                    default:
                        yourPokemon.personalityValue = 0;
                        break;
                }
            }
        });

        //CheckBoxから値を取得
        myScarfCheckBox.setOnCheckedChangeListener(this);
        yourScarfCheckBox.setOnCheckedChangeListener(this);
        myWindCheckBox.setOnCheckedChangeListener(this);
        yourWindCheckBox.setOnCheckedChangeListener(this);
        myGypsumCheckBox.setOnCheckedChangeListener(this);
        yourGypsumCheckBox.setOnCheckedChangeListener(this);

        //計算ボタンがクリックされたとき
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPokemon.level = -1;
                myPokemon.raceValue = -1;
                myPokemon.effortValue = -1;
                myPokemon.individualValue = -1;
                myPokemon.rank = -10;
                myPokemon.rankValue = 0.0;
                yourPokemon.level = -1;
                yourPokemon.raceValue = -1;
                yourPokemon.effortValue = -1;
                yourPokemon.individualValue = -1;
                yourPokemon.rank = -10;
                yourPokemon.rankValue = 0.0;

                EditText myRaceEditText = (EditText)findViewById(R.id.myRaceEditText);
                EditText yourRaceEditText = (EditText)findViewById(R.id.yourRaceEditText);
                EditText myIndividualEditText = (EditText)findViewById(R.id.myIndividual);
                EditText yourIndividualEditText = (EditText)findViewById(R.id.yourIndividual);
                EditText myLevelEditText = (EditText)findViewById(R.id.myLevelEditText);
                EditText yourLevelEditText = (EditText)findViewById(R.id.yourLevelEditText);
                EditText yourEffortEditText = (EditText)findViewById(R.id.yourEffortEditText);
                EditText myRankEditText = (EditText)findViewById(R.id.myRankEditText);
                EditText yourRankEditText = (EditText)findViewById(R.id.yourRankEditText);

                //editTextから値を取得
                if (!myRaceEditText.getText().toString().equals("") && checkStringToInt(myRaceEditText.getText().toString())) myPokemon.raceValue = Integer.parseInt(myRaceEditText.getText().toString());
                if (!yourRaceEditText.getText().toString().equals("") && checkStringToInt(yourRaceEditText.getText().toString())) yourPokemon.raceValue = Integer.parseInt(yourRaceEditText.getText().toString());
                if (!myIndividualEditText.getText().toString().equals("") && checkStringToInt(myIndividualEditText.getText().toString())) myPokemon.individualValue = Integer.parseInt(myIndividualEditText.getText().toString());
                if (!yourIndividualEditText.getText().toString().equals("") && checkStringToInt(yourIndividualEditText.getText().toString())) yourPokemon.individualValue = Integer.parseInt(yourIndividualEditText.getText().toString());
                if (!myLevelEditText.getText().toString().equals("") && checkStringToInt(myLevelEditText.getText().toString())) myPokemon.level = Integer.parseInt(myLevelEditText.getText().toString());
                if (!yourLevelEditText.getText().toString().equals("") && checkStringToInt(yourLevelEditText.getText().toString())) yourPokemon.level = Integer.parseInt(yourLevelEditText.getText().toString());
                if (!yourEffortEditText.getText().toString().equals("") && checkStringToInt(yourEffortEditText.getText().toString())) yourPokemon.effortValue = Integer.parseInt(yourEffortEditText.getText().toString());
                if (!myRankEditText.getText().toString().equals("") && checkRank(myRankEditText.getText().toString())) myPokemon.rank = Integer.parseInt(myRankEditText.getText().toString());
                if (!yourRankEditText.getText().toString().equals("") && checkRank(yourRankEditText.getText().toString())) yourPokemon.rank = Integer.parseInt(yourRankEditText.getText().toString());

                /*if (!myRaceEditText.getText().toString().equals("")) myPokemon.raceValue = Integer.parseInt(myRaceEditText.getText().toString());
                if (!yourRaceEditText.getText().toString().equals("")) yourPokemon.raceValue = Integer.parseInt(yourRaceEditText.getText().toString());
                if (!myIndividualEditText.getText().toString().equals("")) myPokemon.individualValue = Integer.parseInt(myIndividualEditText.getText().toString());
                if (!yourIndividualEditText.getText().toString().equals("")) yourPokemon.individualValue = Integer.parseInt(yourIndividualEditText.getText().toString());
                if (!myLevelEditText.getText().toString().equals("")) myPokemon.level = Integer.parseInt(myLevelEditText.getText().toString());
                if (!yourLevelEditText.getText().toString().equals("")) yourPokemon.level = Integer.parseInt(yourLevelEditText.getText().toString());
                if (!yourEffortEditText.getText().toString().equals("")) yourPokemon.effortValue = Integer.parseInt(yourEffortEditText.getText().toString());
                if (!myRankEditText.getText().toString().equals("")) myPokemon.rankValue = Integer.parseInt(myRankEditText.getText().toString());
                if (!yourRankEditText.getText().toString().equals("")) yourPokemon.rankValue = Integer.parseInt(yourRankEditText.getText().toString());*/

                /*myPokemon.raceValue = Integer.parseInt(myRaceEditText.getText().toString());
                yourPokemon.raceValue = Integer.parseInt(yourRaceEditText.getText().toString());
                myPokemon.individualValue = Integer.parseInt(myIndividualEditText.getText().toString());
                yourPokemon.individualValue = Integer.parseInt(yourIndividualEditText.getText().toString());
                myPokemon.level = Integer.parseInt(myLevelEditText.getText().toString());
                yourPokemon.level = Integer.parseInt(yourLevelEditText.getText().toString());
                yourPokemon.effortValue = Integer.parseInt(yourEffortEditText.getText().toString());
                myPokemon.rankValue = Integer.parseInt(myRankEditText.getText().toString());
                yourPokemon.rankValue = Integer.parseInt(yourRankEditText.getText().toString());*/

                if (myPokemon.raceValue < 0 || yourPokemon.raceValue < 0) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("ERROR")
                            .setMessage("種族値を正しく入力してください")
                            .setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {}
                                    }
                            )
                            .show();
                } else if (myPokemon.individualValue < 0 || myPokemon.individualValue > 31 || yourPokemon.individualValue < 0 || yourPokemon.individualValue > 31) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("ERROR")
                            .setMessage("個体値の値を正しく入力してください")
                            .setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }
                            )
                            .show();
                } else if (myPokemon.level < 0 || myPokemon.level > 100 || yourPokemon.level < 0 || yourPokemon.level > 100) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("ERROR")
                            .setMessage("Lvの値を正しく入力してください")
                            .setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }
                            )
                            .show();
                } else if (yourPokemon.effortValue < 0 || yourPokemon.effortValue > 252) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("ERROR")
                            .setMessage("相手の努力値の値を正しく入力してください")
                            .setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }
                            )
                            .show();
                } else if (myPokemon.rank < -6 || myPokemon.rank > 6 || yourPokemon.rank < -6 || yourPokemon.rank > 6) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("ERROR")
                            .setMessage("ランク補正の値を正しく入力してください")
                            .setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }
                            )
                            .show();
                } else {
                    //ランク補正の値を設定
                    myPokemon.rankToRankValue(myPokemon.rank);
                    yourPokemon.rankToRankValue(yourPokemon.rank);

                    //相手のポケモンの能力値を求める
                    yourPokemon.calculateAbility();
                    //yourPokemon.calculateRankAbility();

                    //相手の能力値よりも1大きくなるために必要な努力値を求める
                    /*myPokemon.rankAbilityValue = yourPokemon.rankAbilityValue + 1;
                    myPokemon.calculateAbilityFromRankAbility();
                    myPokemon.calculateEffortFromAbilityValue();*/
                    myPokemon.calculateSpeedEffort(yourPokemon);

                    String s;
                    if (myPokemon.effortValue == 260) {
                        s = String.format("先に行動するには努力値が %d 必要です．同速なら可能です．", myPokemon.effortValue);
                    } else if (myPokemon.effortValue > 260) {
                        s = String.format("先に行動するには努力値が %d 必要です．先に行動できません．", myPokemon.effortValue);
                    } else if (myPokemon.effortValue <= 0) {
                        s = String.format("先に行動するには努力値が %d 必要です．努力値を振る必要はありません．", myPokemon.effortValue);
                    } else {
                        s = String.format("先に行動するには努力値が %d 必要です．", myPokemon.effortValue);
                    }

                    //AlertDialogで結果表示
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("RESULT")
                            .setMessage(s)
                            .setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {}
                                    }
                            )
                            .show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText myRaceEditText = (EditText)findViewById(R.id.myRaceEditText);
                EditText yourRaceEditText = (EditText)findViewById(R.id.yourRaceEditText);
                EditText myIndividualEditText = (EditText)findViewById(R.id.myIndividual);
                EditText yourIndividualEditText = (EditText)findViewById(R.id.yourIndividual);
                EditText myLevelEditText = (EditText)findViewById(R.id.myLevelEditText);
                EditText yourLevelEditText = (EditText)findViewById(R.id.yourLevelEditText);
                EditText yourEffortEditText = (EditText)findViewById(R.id.yourEffortEditText);
                EditText myRankEditText = (EditText)findViewById(R.id.myRankEditText);
                EditText yourRankEditText = (EditText)findViewById(R.id.yourRankEditText);

                myRaceEditText.setText("");
                yourRaceEditText.setText("");
                myIndividualEditText.setText("");
                yourIndividualEditText.setText("");
                myLevelEditText.setText("");
                yourLevelEditText.setText("");
                yourEffortEditText.setText("");
                myRankEditText.setText("");
                yourRankEditText.setText("");
                myScarfCheckBox.setChecked(false);
                yourScarfCheckBox.setChecked(false);
                myWindCheckBox.setChecked(false);
                yourWindCheckBox.setChecked(false);
                myGypsumCheckBox.setChecked(false);
                yourGypsumCheckBox.setChecked(false);
            }
        });

    }

    public static boolean checkStringToInt(String s) {
        boolean isDigit = true;

        for (int i = 0; i < s.length(); i++) {
            isDigit = Character.isDigit(s.charAt(i));
            if (!isDigit) break;
        }

        return isDigit;
    }

    public static boolean checkRank(String s) {
        boolean isDigit = true;

        for (int i = 0; i < s.length(); i++) {
            isDigit = Character.isDigit(s.charAt(i));

            if (i == 0) {
                if (s.charAt(0) == '-') isDigit = true;
            }

            if (!isDigit) break;
        }

        return isDigit;
    }

    //CheckBoxにチェックがされた，もしくは外されたとき呼び出されるメソッド
    @Override
    public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
        if (myScarfCheckBox.isChecked()) {
            myPokemon.itemValue = 1.5;
        } else  {
            myPokemon.itemValue = 1.0;
        }
        if (yourScarfCheckBox.isChecked()) {
            yourPokemon.itemValue = 1.5;
        } else {
            yourPokemon.itemValue = 1.0;
        }
        if (myWindCheckBox.isChecked()) {
            myPokemon.fieldValue = 1.5;
        } else {
            myPokemon.fieldValue = 1.0;
        }
        if (yourWindCheckBox.isChecked()) {
            yourPokemon.fieldValue = 1.5;
        } else {
            yourPokemon.fieldValue = 1.0;
        }
        if (myGypsumCheckBox.isChecked()) {
            myPokemon.gypsumValue = 1.5;
        } else {
            myPokemon.gypsumValue = 1.0;
        }
        if (yourGypsumCheckBox.isChecked()) {
            yourPokemon.gypsumValue = 1.5;
        } else {
            yourPokemon.gypsumValue = 1.0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        TextView varTextView = (TextView)findViewById(R.id.myRaceEditText);
        switch (item.getItemId()) {
            case R.id.item1:
                varTextView.setText(R.string.menu_item1);
                return true;
            case R.id.item2:
                varTextView.setText(R.string.menu_item2);
                return true;
            case R.id.item3:
                varTextView.setText(R.string.menu_item3);
                return true;
            case R.id.item4:
                varTextView.setText(R.string.menu_item4);
                return true;
            case R.id.item5:
                varTextView.setText(R.string.menu_item5);
                return true;
            case R.id.item6:
                varTextView.setText("こんにちは");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
