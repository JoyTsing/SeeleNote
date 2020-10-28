package engine

type ParseResult struct {
	Request []Request
	Items []interface{}
}

type Request struct {
	Url string
	ParserFunc func([]byte) ParseResult
}

func NilParser([]byte)ParseResult{
	return ParseResult{}
}