package parser

import (
	"joytsing/learngo/crawler/engine"
	"regexp"
)

const cityListRe = `<a href="(http://www.zhenai.com/zhenghun/[0-9a-z]+)"[^>]*>([^<]+)</a>`

func ParseCityList(contents []byte) engine.ParseResult {
	re := regexp.MustCompile(cityListRe)
	matches := re.FindAllSubmatch(contents, -1)
	result := engine.ParseResult{}

	for _, m := range matches {
		result.Items = append(result.Items,"city "+string(m[2]))
		result.Request = append(result.Request, engine.Request{
			Url:        string(m[1]),
			ParserFunc: ParseCity,
		})
		//fmt.Printf("City: %s,URL: %s\n",m[2],m[1])
	}
	//fmt.Printf("Matches found: %d\n",len(matches))
	//fmt.Printf("len of city %d\n",len(result.Items))

	return result
}
