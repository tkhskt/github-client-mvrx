package com.gericass.githubclientmvrx.data.remote

const val ACCEPT_HEADER = "Accept"
const val TOKEN_HEADER = "Authorization"

const val URL_GET_TOKEN = "/login/oauth/access_token"
const val GET_EVENTS = "/events"
const val GET_RECEIVE_EVENT = "/users/{user_name}/received_events"
const val GET_LOGIN_USER = "/user"
const val GET_SEARCH_REPOS = "/search/repositories"