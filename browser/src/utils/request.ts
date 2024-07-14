import axios from 'axios'

const requrest = axios.create({
  baseURL: 'http://localhost',
  timeout: 1000,
  withCredentials: true
})

export { requrest }
