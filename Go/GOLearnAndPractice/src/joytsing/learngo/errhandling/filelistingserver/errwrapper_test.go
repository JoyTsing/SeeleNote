package main

import (
	"errors"
	"fmt"
	"io/ioutil"
	"net/http"
	"net/http/httptest"
	"os"
	"strings"
	"testing"
)

type testingUserError string

func errPanic(writer http.ResponseWriter,
	request *http.Request) error {
	panic(123)
}

func errUserError(writer http.ResponseWriter,
	request *http.Request) error {
	return testingUserError("user error")
}

func (e testingUserError) Error() string {
	return e.Message()
}

func (e testingUserError) Message() string {
	return string(e)
}

func errNotFound(writer http.ResponseWriter,
	request *http.Request) error {
	return os.ErrNotExist
}

func errUnknown(writer http.ResponseWriter,
	request *http.Request) error {
	return errors.New("unknown error")
}

func errNoPermission(writer http.ResponseWriter,
	request *http.Request) error {
	return os.ErrPermission
}

func noError(writer http.ResponseWriter,
	request *http.Request) error {
	fmt.Fprintln(writer, "No Error")
	return nil
}

var tests = []struct {
	h       appHandler
	code    int
	message string
}{
	{errPanic, 500, "Internal Server Error"},
	{errUserError, 400, "user error"},
	{errNotFound, 404, "Not Found"},
	{errNoPermission, 403, "Forbidden"},
	{errUnknown, 500, "Internal Server Error"},
	{noError, 200, "No Error"},
}

func TestErrWrapper(t *testing.T) {

	for _, tt := range tests {
		f := errWrapper(tt.h)
		response := httptest.NewRecorder()
		request := httptest.NewRequest(
			http.MethodGet, "htto://www.imooc.com", nil)
		f(response, request)

		verifyResponse(response.Result(),tt.code,tt.message,t)
	}
}

func TestErrWrapperInServer(t *testing.T) {
	for _,tt:=range tests{
		f:=errWrapper(tt.h)
		server:=httptest.NewServer(http.HandlerFunc(f))
		response,_:=http.Get(server.URL)
		verifyResponse(response,tt.code,tt.message,t)
	}
}

func verifyResponse(resp *http.Response,expectedCode int, expectedMsg string,t *testing.T) {
	b, _ := ioutil.ReadAll(resp.Body)
	body := strings.Trim(string(b), "\n")
	if resp.StatusCode != expectedCode ||
		body != expectedMsg {
		t.Errorf("expect(%d,%s) "+" got (%d, %s)", expectedCode, expectedMsg, resp.StatusCode, body)
	}
}
