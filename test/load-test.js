import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 100 },
        { duration: '30s', target: 500 },
        { duration: '10s', target: 0 },
    ],
    thresholds: {
        http_req_failed: ['rate<0.01'],
        http_req_duration: ['p(95)<500'],
    },
};

const BASE_URL = 'http://api.shopgrid';
const USERNAME = 'testuser111';
const PASSWORD = 'password';

export function setup() {
    const loginPayload = JSON.stringify({
        username: USERNAME,
        password: PASSWORD,
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const res = http.post(`${BASE_URL}/api/v1/auth/login`, loginPayload, params);

    check(res, {
        'Logged in successfully': (r) => r.status === 200,
    });

    return { token: res.json('access_token') };
}

export default function (data) {
    const params = {
        headers: {
            'Authorization': `Bearer ${data.token}`,
        },
    };

    const res = http.get(`${BASE_URL}/api/v1/products`, params);

    check(res, {
        'Status is 200': (r) => r.status === 200,
    });

    sleep(1);
}