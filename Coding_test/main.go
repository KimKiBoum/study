package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
	"net/url"
)

func main() {
	dat, err := ioutil.ReadFile("passwd_reset_mail_form.html")
	if err != nil {
		panic(err)
	}

	resp, err := http.PostForm("http://noti.sdn.test.airensoft.com:57000/api/v1/email", url.Values{"SENDER": {"03rlarlqja@naver.com"}, "RECEIVERS": {"rlarl7477@atlasnetworks.co.kr"}, "SUBJECT": {"비밀번호 초기화 테스트"}, "CONTENT": {string(dat)}})

	if err != nil {
		fmt.Printf("error : ")
		panic(err)
	}

	// resp2, err := http.Get("http://61.255.238.80:7443/v2/portal/member")

	if err != nil {
		panic(err)
	}

	fmt.Println(resp)
	fmt.Println("-------------------------------------")
	fmt.Println(resp2)
}
