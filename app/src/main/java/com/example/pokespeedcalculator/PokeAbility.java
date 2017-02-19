package com.example.pokespeedcalculator;

public class PokeAbility {
  public int level;                 //レベル
  public int raceValue;             //種族値
  public int effortValue;           //努力値
  public int individualValue;       //個体値
  public double personalityValue;   //性格値0.9, 1.0, 1.1
  public int rank;                  //ランク-6 ~ 6
  public double rankValue;          //ランク補正値

  public double itemValue;          //アイテム補正値（ギプス類除く)
  public double gypsumValue;        //ギプス，パワー系による素早さ補正値
  public double fieldValue;         //フィールド補正値（おいかぜ等）

  public int abilityValue;          //能力値（実数値）
  public int rankAbilityValue;      //戦闘中の補正を含めた能力値

  public PokeAbility() {
    this.level = -1;
    this.raceValue = -1;
    this.effortValue = -1;
    this.individualValue = -1;
    this.personalityValue = 1.0;
    this.rank = 0;
    this.rankValue = 0.0;
    this.itemValue = 1.0;
    this.gypsumValue = 1.0;
    this.fieldValue = 1.0;
  }

  public void rankToRankValue(int rank) {
    switch (rank) {
      case -6:
        this.rankValue = 2.0 / 8;
        break;
      case -5:
        this.rankValue = 2.0 / 7;
        break;
      case -4:
        this.rankValue = 2.0 / 6;
        break;
      case -3:
        this.rankValue = 2.0 / 5;
        break;
      case -2:
        this.rankValue = 2.0 / 4;
        break;
      case -1:
        this.rankValue = 2.0 / 3;
        break;
      case 0:
        this.rankValue = 1.0;
        break;
      case 1:
        this.rankValue = 3.0 / 2;
        break;
      case 2:
        this.rankValue = 4.0 / 2;
        break;
      case 3:
        this.rankValue = 5.0 / 2;
        break;
      case 4:
        this.rankValue = 6.0 / 2;
        break;
      case 5:
        this.rankValue = 7.0 / 2;
        break;
      case 6:
        this.rankValue = 8.0 / 2;
        break;
      default:
        this.rankValue = 0;
        break;
    }
  }

  public void calculateHp() { //hpの実数値計算
    this.abilityValue = (this.raceValue * 2 + this.individualValue + this.effortValue / 4) * this.level / 100 + this.level + 10;
  }

  public void calculateAbility() {  //hp以外の実数値計算
    int value = (this.raceValue * 2 + this.individualValue + this.effortValue / 4) * this.level / 100 + 5;
    double value2 = value * this.personalityValue;
    this.abilityValue = (int)value2;
  }

  public void calculateRankAbility() {  //戦闘中の補正を含めた能力値
    double value = this.abilityValue * this.rankValue;
    int value2 = (int)value;
    value = value2 * this.itemValue;
    value2 = (int)value;
    value = value2 * this.gypsumValue;
    value2 = (int)value;
    value = value2 * this.fieldValue;
    this.rankAbilityValue = (int)value;
  }

  public void calculateAbilityFromRankAbility() {
    double value = this.rankAbilityValue / this.rankValue;
    int value2 = (int)value;
    value = value2 / this.itemValue;
    value2 = (int)value;
    value = value2 / this.gypsumValue;
    value2 = (int)value;
    value = value2 / this.fieldValue;
    this.abilityValue = (int)value;
  }

  public void calculateEffortFromAbilityValue() {
    double value = (double)this.abilityValue / this.personalityValue;
    int value2 = (int)value;
    value = 400 * (value2 - 5) / this.level;
    value2 = (int)value;
    this.effortValue = value2 - 8 * this.raceValue - 4 * this.individualValue;
  }

  public void calculateSpeedEffort(PokeAbility yourPokemon) { //相手の素早さ実数値から必要な自分の素早さ努力値を求める
    double item = this.itemValue / yourPokemon.itemValue;
    double field = this.fieldValue / yourPokemon.fieldValue;
    double gypsum = this.gypsumValue / yourPokemon.gypsumValue;
    double rank = this.rankValue / yourPokemon.rankValue;
    double value = (yourPokemon.abilityValue + 1) / rank;
    int value2 = (int)value;
    value = value2 / gypsum;
    value2 = (int)value;
    value = value2 / field;
    value2 = (int)value;
    value = value2 / item;
    this.abilityValue = (int)value;
    value = this.abilityValue / this.personalityValue;
    value = Math.ceil(value);
    value2 = (int)value;
    this.effortValue = 400 * (value2 - 5) / this.level - 8 * this.raceValue - 4 * this.individualValue;
  }

  /*public static void main(String[] args) {
    PokeAbility myPokemon = new PokeAbility();

    myPokemon.level = 50;
    myPokemon.raceValue = 80;
    myPokemon.individualValue = 31;
    myPokemon.effortValue = 172;
    myPokemon.personalityValue = 1.1;

    myPokemon.rank = 1;
    myPokemon.rankToRankValue(myPokemon.rank);
    System.out.println(myPokemon.rank);
    System.out.println(myPokemon.rankValue);

    myPokemon.calculateAbility();
    myPokemon.calculateRankAbility();

    System.out.println(myPokemon.rankAbilityValue);
  }*/
}
