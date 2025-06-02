1. Critical bugs in endpoint GET /player/create/{editor} :
   - All secret fields better do not send in query parameters for security, its should be in request body.
   Also better to use POST method for this endpoint by convention.
   - twice login - rewrite user data  (can rewrite another user all data)
   - twice screenName - create new user with same screenName
2. endpoint  POST /player/get :
   - critical in some calls response time is over 10 seconds, possible something with index optimization in BD.
   - Also better to use GET method for this endpoint by convention.
3. Error response :
   - 403 errors response without response body, but should be.
    -Sometimes 400 error return without body in response on same request call.
4. Other bugs are marked in code comments with tag fixme.