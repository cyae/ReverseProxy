local ngx = require("ngx")
local http = require("resty.http")

local http_client = http:new()

local full_uri = ngx.var.scheme .. "://" .. ngx.var.host .. ":8081" .. ngx.vat.request_uri
ngx.req.read_body()

local resp, err = http_client:request_uri(full_uri, {
    method = ngx.var.request_method,
    headers = ngx.req.raw_header(),
    body = ngx.req.get_body_data()
})

if not resp then
    ngx.say("request error", err)
    return
end

ngx.say(resp.body)
function async_store()
    local s = full_uri .. "|" .. resp.body
    local vertx_uri = "http://127.0.0.1:8181"
    local resp1, err1 = http_client:request_uri(vertx_uri, {
        method = "GET",
        body = s
    })


    if not resp1 then
        ngx.say("request error", err1)
        return
    end

    ngx.say(resp1.body)

end

local co = coroutine.create(async_store())
coroutine.resume(co)
