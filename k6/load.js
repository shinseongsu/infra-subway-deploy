import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  stages: [
    { duration: '5m', target: 10 },
    { duration: '5m', target: 70 },
    { duration: '5m', target: 50 },
    { duration: '5m', target: 70 },
    { duration: '5m', target: 10 },
    { duration: '5m', target: 30 },
  ],

  thresholds: {
    http_req_duration: ['p(99)<500'], // 99% of requests must complete below 0.5s
  },
};

const BASE_URL = 'https://sss-next-step.o-r.kr/';
const USERNAME = 'tlstjdtn321@naver.com';
const PASSWORD = '1234';

export default function ()  {
  let token = login();

  let authHeaders = {
    headers: {
      Authorization: `Bearer ` + token,
      'Content-Type': 'application/json'
    },
  };

  updateMyInfo(authHeaders);
  findPath(1, 4);

  sleep(1);
};

function login() {
  var payload = JSON.stringify({
    email: USERNAME,
    password: PASSWORD,
  });

  var params = {
    headers: {
      'Content-Type': 'application/json'
    },
  };

  let loginRes = http.post(`${BASE_URL}/login/token`, payload, params);

  check(loginRes, {
    'logged in successfully': (resp) => resp.json('accessToken') !== '',
  });
  return loginRes.json('accessToken');
}

function updateMyInfo(authHeaders) {
  let updateRequest = JSON.stringify({
    email: USERNAME,
    password: PASSWORD,
    age: 30,
  });

  let response = http.put(`${BASE_URL}/members/me`, updateRequest, authHeaders);
  check(response, { 'update my info': (resp) => resp.status === 200});
}

function findPath(source, target) {
  let response = http.get(`${BASE_URL}/paths?source=${source}&target=${target}`);
  check(response, { 'findPath success': (resp) => resp.status === 200});
}