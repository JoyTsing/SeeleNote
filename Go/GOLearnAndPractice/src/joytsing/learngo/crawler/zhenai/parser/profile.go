package parser

import (
	"joytsing/learngo/crawler/engine"
	"joytsing/learngo/crawler/model"
	"regexp"
	"strconv"
)

/*
type Profile struct {
	Name       string
	Age        int
	Height     int
	Income     string
	Marriage   string
	Hukou      string
	Xinzuo     string
}
*/
//<div class="m-btn purple" data-v-8b1eac0c>49kg</div>
var ageRe = regexp.MustCompile(`<div class="m-btn purple" data-v-8b1eac0c>([\d-]+)岁</div>`)
var marriageRe = regexp.MustCompile(`<div class="m-btn purple" data-v-8b1eac0c>([^<]+)</div>`)
var xinzuoRe=regexp.MustCompile(`<div class="m-btn purple" data-v-8b1eac0c>([^<]+座)[^<]+</div>`)
var heightRe=regexp.MustCompile(`<div class="m-btn purple" data-v-8b1eac0c>([\d-]+)cm</div>`)
var incomeRe=regexp.MustCompile(`<div class="m-btn purple" data-v-8b1eac0c>月收入:([\d-.]+[^<]+)</div>`)//ok
var hukouRe=regexp.MustCompile(`<div class="m-btn pink" data-v-8b1eac0c>籍贯:([^<]+)</div>`)//ok

func ParseProfile(content []byte,name string) engine.ParseResult {
	profile := model.Profile{}

	//通过函数式编程 name从上一级列表中取得
	profile.Name=name
	profile.Xinzuo=extractString(content,xinzuoRe)
	profile.Marriage=extractString(content,marriageRe)
	profile.Income=extractString(content,incomeRe)
	profile.Hukou=extractString(content,hukouRe)

	if height, err := strconv.Atoi(extractString(content, heightRe)); err == nil {
		profile.Height=height
	}

	if age, err := strconv.Atoi(extractString(content, ageRe)); err == nil {
		profile.Age = age
	}

	result:=engine.ParseResult{
		Items: []interface{}{profile},
	}

	return result
}

func extractString(contents []byte, re *regexp.Regexp) string {
	match := re.FindSubmatch(contents)
	if len(match) >= 2 {
		return string(match[1])
	} else {
		return ""
	}

}
