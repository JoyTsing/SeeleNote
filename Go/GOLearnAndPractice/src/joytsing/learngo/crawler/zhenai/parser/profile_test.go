package parser

import (
	"io/ioutil"
	"joytsing/learngo/crawler/model"
	"testing"
)

func TestParseProfile(t *testing.T) {
	contents,err:=ioutil.ReadFile("profile_test_data.html")

	if err!=nil{
		panic(err)
	}
	//fmt.Printf("%s\n",contents)
	result:=ParseProfile(contents,"墨香")
	
	if len(result.Items)!=1{
		t.Errorf("Item should contain 1 "+"element; but was %v",result.Items)
	}
	profile:=result.Items[0].(model.Profile)
	expected:=model.Profile{
		Name:     "墨香",
		Age:      33,
		Height:   158,
		Income:   "5-8千",
		Marriage: "未婚",
		Hukou:    "黑龙江牡丹江",
		Xinzuo:   "射手座",
	}

	if profile!=expected{
		t.Errorf("Name should be %s ,but is %s",expected.Name,profile.Name)
		t.Errorf("Age should be %d ,but is %d",expected.Age,profile.Age)
		t.Errorf("Height should be %d ,but is %d",expected.Height,profile.Height)
		t.Errorf("Income should be %s ,but is %s",expected.Income,profile.Income)
		t.Errorf("Marriage should be %s ,but is %s",expected.Marriage,profile.Marriage)
		t.Errorf("Hukou should be %s ,but is %s",expected.Hukou,profile.Hukou)
		t.Errorf("XinZuo should be %s ,but is %s",expected.Xinzuo,profile.Xinzuo)
	}
}
