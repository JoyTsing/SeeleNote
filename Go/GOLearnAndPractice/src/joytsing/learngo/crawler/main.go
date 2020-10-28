package main

import (
	"joytsing/learngo/crawler/engine"
	"joytsing/learngo/crawler/zhenai/parser"
)

func main() {
	//concurrentEngine := engine.ConcurrentEngine{
	//	Scheduler: &scheduler.QueuedScheduler{},
	//	WorkerCount: 10,
	//}
	simpleEngine := engine.SimpleEngine{
	}
	simpleEngine.Run(engine.Request{
		Url:        "http://www.zhenai.com/zhenghun/shanghai",
		ParserFunc: parser.ParseCity,
	})
	//concurrentEngine.Run(engine.Request{
	//	Url:        "http://www.zhenai.com/zhenghun",
	//	ParserFunc: parser.ParseCityList,
	//})

	//concurrentEngine.Run(engine.Request{
	//	Url: "http://www.zhenai.com/zhenghun/shanghai",
	//	ParserFunc: parser.ParseCity,
	//})
}
