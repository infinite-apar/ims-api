# IMS Backend API Documentation

**Base URL:** `http://localhost:8080`  
**Version:** 1.0  
**Last Updated:** November 27, 2025

---

## Table of Contents
1. [Authentication](#authentication)
2. [Common Response Format](#common-response-format)
3. [Error Codes](#error-codes)
4. [API Endpoints](#api-endpoints)
   - [Auth Endpoints](#auth-endpoints)
   - [User Endpoints](#user-endpoints)
   - [Role Endpoints](#role-endpoints)
   - [Post Endpoints](#post-endpoints)
   - [Comment Endpoints](#comment-endpoints)
   - [Dashboard Endpoints](#dashboard-endpoints)

---

## Authentication

This API uses **JWT (JSON Web Token)** authentication.

### How to Authenticate
1. Register or login to get a JWT token
2. Include the token in all subsequent requests:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

### Token Details
- **Expiration:** 24 hours (86400000 ms)
- **Type:** Bearer Token
- **Storage:** Store securely in frontend (e.g., localStorage, sessionStorage, or httpOnly cookie)

---

## Common Response Format

All API responses follow this structure:

```json
{
  "status": 200,
  "message": "Success message",
  "data": { },
  "timestamp": "2025-11-27T10:30:00",
  "pagination": { },
    "currentPage": 0,
    "totalPages": 5,
    "totalElements": 50,
    "pageSize": 10
  
}
```

---

## Error Codes

| Status Code | Description |
|-------------|-------------|
| 200 | Success |
| 201 | Resource created successfully |
| 204 | Success with no content |
| 400 | Bad request (validation error) |
| 401 | Unauthorized (missing or invalid token) |
| 403 | Forbidden (insufficient permissions) |
| 404 | Resource not found |
| 500 | Internal server error |

### Error Response Format
```json
{
  "status": 400,
  "message": "Error description",
  "timestamp": "2025-11-27T10:30:00"
}
```

---

## API Endpoints

---

## Auth Endpoints

### 1. Register User
**POST** `/auth/register`

Register a new user account.

**Headers:** None required

**Request Body:**
```json
{
  "username": "john_doe",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@infinite.com",
  "phoneNumber": 1234567890
}
```

**Validation Rules:**
- `username`: 3-30 characters, required
- `password`: 6-100 characters, required
- `email`: Must be valid @infinite.com email, required
- `phoneNumber`: Positive number

**Response (201):**
```json
{
  "status": 201,
  "message": "User registered successfully",
  "data": {
    "id": 1,
    "username": "john_doe",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@infinite.com",
    "phoneNumber": 1234567890,
    "roles": [
      {
        "id": 2,
        "roleName": "ROLE_USER"
      }
    ]
  },
  "timestamp": "2025-11-27T10:30:00"
}
```

---

### 2. Login
**POST** `/auth/login`

Authenticate user and receive JWT token.

**Headers:** None required

**Request Body:**
```json
{
  "username": "john_doe",
  "password": "password123"
}
```

**Response (200):**
```json
{
  "status": 200,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "expiresIn": 86400000,
    "username": "john_doe",
    "email": "john.doe@infinite.com",
    "roles": ["ROLE_USER"]
  },
  "timestamp": "2025-11-27T10:30:00"
}
```

**Usage:**
Store the token and use it in subsequent requests:
```javascript
const token = response.data.token;
// Include in all API calls:
headers: {
  'Authorization': `Bearer ${token}`
}
```

---

## User Endpoints

### 3. Get All Users
**GET** `/api/users`

Retrieve all users (Admin only).

**Headers:** 
```
Authorization: Bearer <token>
```

**Required Role:** `ROLE_ADMIN`

**Response (200):**
```json
{
  "status": 200,
  "message": "Users retrieved successfully",
  "data": [
    {
      "id": 1,
      "username": "john_doe",
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@infinite.com",
      "phoneNumber": 1234567890,
      "roles": [
        {
          "id": 2,
          "roleName": "ROLE_USER"
        }
      ]
    }
  ],
  "timestamp": "2025-11-27T10:30:00"
}
```

---

## Role Endpoints

### 4. Get All Roles
**GET** `/api/roles`

Retrieve all available roles (Admin only).

**Headers:** 
```
Authorization: Bearer <token>
```

**Required Role:** `ROLE_ADMIN`

**Response (200):**
```json
{
  "status": 200,
  "message": "Roles fetched successfully",
  "data": [
    {
      "id": 1,
      "roleName": "ROLE_ADMIN"
    },
    {
      "id": 2,
      "roleName": "ROLE_USER"
    }
  ],
  "timestamp": "2025-11-27T10:30:00"
}
```

---

### 5. Create Role
**POST** `/api/roles`

Create a new role (Admin only).

**Headers:** 
```
Authorization: Bearer <token>
```

**Required Role:** `ROLE_ADMIN`

**Request Body:**
```json
{
  "roleName": "ROLE_MODERATOR"
}
```

**Response (201):**
```json
{
  "status": 201,
  "message": "Role added successfully",
  "data": {
    "id": 3,
    "roleName": "ROLE_MODERATOR"
  },
  "timestamp": "2025-11-27T10:30:00"
}
```

---

## Post Endpoints

### Post Status Enum Values
- `DRAFT` - Post saved as draft
- `PENDING_APPROVAL` - Submitted for approval
- `APPROVED` - Approved by admin
- `REJECTED` - Rejected by admin
- `RESOLVED` - Issue resolved

### Post Tag Enum Values
- `ISSUE` - Report an issue
- `COMPLAINT` - File a complaint
- `ANNOUNCEMENT` - Make an announcement
- `GENERAL` - General post

---

### 6. Create Post
**POST** `/api/posts`

Create a new post.

**Headers:** 
```
Authorization: Bearer <token>
```

**Request Body:**
```json
{
  "title": "Network connectivity issue",
  "content": "Unable to connect to VPN from office network",
  "type": "ISSUE"
}
```

**Validation Rules:**
- `title`: Max 255 characters, required
- `content`: Required
- `type`: Must be one of: ISSUE, COMPLAINT, ANNOUNCEMENT, GENERAL

**Response (201):**
```json
{
  "status": 201,
  "message": "Post created successfully",
  "data": {
    "id": 1,
    "title": "Network connectivity issue",
    "content": "Unable to connect to VPN from office network",
    "type": "ISSUE",
    "status": "DRAFT",
    "createdByUsername": "john_doe",
    "createdById": 1,
    "createdAt": "2025-11-27T10:30:00",
    "updatedAt": "2025-11-27T10:30:00",
    "submittedAt": null,
    "resolvedAt": null
  },
  "timestamp": "2025-11-27T10:30:00"
}
```

---

### 7. Get All Posts
**GET** `/api/posts?page=0&size=10`

Retrieve all posts with pagination.

**Headers:** 
```
Authorization: Bearer <token>
```

**Query Parameters:**
- `page` (optional): Page number, default: 0
- `size` (optional): Items per page, default: 10

**Response (200):**
```json
{
  "status": 200,
  "message": "Posts fetched successfully",
  "data": [
    {
      "id": 1,
      "title": "Network connectivity issue",
      "content": "Unable to connect to VPN from office network",
      "type": "ISSUE",
      "status": "APPROVED",
      "createdByUsername": "john_doe",
      "createdById": 1,
      "createdAt": "2025-11-27T10:30:00",
      "updatedAt": "2025-11-27T11:00:00",
      "submittedAt": "2025-11-27T10:45:00",
      "resolvedAt": null
    }
  ],
  "timestamp": "2025-11-27T10:30:00",
  "pagination": {
    "currentPage": 0,
    "totalPages": 5,
    "totalElements": 50,
    "pageSize": 10
  }
}
```

---

### 8. Get Post by ID
**GET** `/api/posts/{id}`

Retrieve a specific post by ID.

**Headers:** 
```
Authorization: Bearer <token>
```

**Path Parameters:**
- `id`: Post ID

**Response (200):**
```json
{
  "status": 200,
  "message": "Post fetched successfully",
  "data": {
    "id": 1,
    "title": "Network connectivity issue",
    "content": "Unable to connect to VPN from office network",
    "type": "ISSUE",
    "status": "APPROVED",
    "createdByUsername": "john_doe",
    "createdById": 1,
    "createdAt": "2025-11-27T10:30:00",
    "updatedAt": "2025-11-27T11:00:00",
    "submittedAt": "2025-11-27T10:45:00",
    "resolvedAt": null
  },
  "timestamp": "2025-11-27T10:30:00"
}
```

---

### 9. Update Post
**PUT** `/api/posts/{id}`

Update an existing post. Only the post owner can update.

**Headers:** 
```
Authorization: Bearer <token>
```

**Path Parameters:**
- `id`: Post ID

**Request Body:**
```json
{
  "title": "Updated title",
  "content": "Updated content with more details",
  "type": "ISSUE"
}
```

**Response (200):**
```json
{
  "status": 200,
  "message": "Post updated successfully",
  "data": {
    "id": 1,
    "title": "Updated title",
    "content": "Updated content with more details",
    "type": "ISSUE",
    "status": "DRAFT",
    "createdByUsername": "john_doe",
    "createdById": 1,
    "createdAt": "2025-11-27T10:30:00",
    "updatedAt": "2025-11-27T12:00:00",
    "submittedAt": null,
    "resolvedAt": null
  },
  "timestamp": "2025-11-27T12:00:00"
}
```

---

### 10. Update Post Status
**PATCH** `/api/posts/{id}/status`

Update post status. Permissions:
- **Owner:** Can change DRAFT → PENDING_APPROVAL
- **Admin:** Can change to any status

**Headers:** 
```
Authorization: Bearer <token>
```

**Path Parameters:**
- `id`: Post ID

**Request Body:**
```json
{
  "status": "PENDING_APPROVAL"
}
```

**Response (200):**
```json
{
  "status": 200,
  "message": "Post status updated successfully",
  "data": {
    "id": 1,
    "title": "Network connectivity issue",
    "content": "Unable to connect to VPN from office network",
    "type": "ISSUE",
    "status": "PENDING_APPROVAL",
    "createdByUsername": "john_doe",
    "createdById": 1,
    "createdAt": "2025-11-27T10:30:00",
    "updatedAt": "2025-11-27T12:00:00",
    "submittedAt": "2025-11-27T12:00:00",
    "resolvedAt": null
  },
  "timestamp": "2025-11-27T12:00:00"
}
```

---

### 11. Delete Post
**DELETE** `/api/posts/{id}`

Delete a post. Only the post owner or admin can delete.

**Headers:** 
```
Authorization: Bearer <token>
```

**Path Parameters:**
- `id`: Post ID

**Response (204):**
```json
{
  "status": 204,
  "message": "Post deleted successfully",
  "timestamp": "2025-11-27T12:00:00"
}
```

---

## Comment Endpoints

### 12. Create Comment
**POST** `/api/posts/{postId}/comments`

Add a comment to a post.

**Headers:** 
```
Authorization: Bearer <token>
```

**Path Parameters:**
- `postId`: Post ID to comment on

**Request Body:**
```json
{
  "content": "I'm experiencing the same issue"
}
```

**Validation Rules:**
- `content`: Required, cannot be blank

**Response (201):**
```json
{
  "status": 201,
  "message": "Comment created successfully",
  "data": {
    "id": 1,
    "content": "I'm experiencing the same issue",
    "postId": 1,
    "createdById": 1,
    "createdByUsername": "john_doe",
    "createdAt": "2025-11-27T13:00:00",
    "updatedAt": "2025-11-27T13:00:00"
  },
  "timestamp": "2025-11-27T13:00:00"
}
```

---

### 13. Get Comments by Post
**GET** `/api/posts/{postId}/comments?page=0&size=10`

Retrieve all comments for a specific post with pagination.

**Headers:** 
```
Authorization: Bearer <token>
```

**Path Parameters:**
- `postId`: Post ID

**Query Parameters:**
- `page` (optional): Page number, default: 0
- `size` (optional): Items per page, default: 10

**Response (200):**
```json
{
  "status": 200,
  "message": "Comments retrieved successfully",
  "data": [
    {
      "id": 1,
      "content": "I'm experiencing the same issue",
      "postId": 1,
      "createdById": 1,
      "createdByUsername": "john_doe",
      "createdAt": "2025-11-27T13:00:00",
      "updatedAt": "2025-11-27T13:00:00"
    }
  ],
  "timestamp": "2025-11-27T13:00:00",
  "pagination": {
    "currentPage": 0,
    "totalPages": 2,
    "totalElements": 15,
    "pageSize": 10
  }
}
```

---

### 14. Get Comment by ID
**GET** `/api/comments/{id}`

Retrieve a specific comment by ID.

**Headers:** 
```
Authorization: Bearer <token>
```

**Path Parameters:**
- `id`: Comment ID

**Response (200):**
```json
{
  "status": 200,
  "message": "Comment retrieved successfully",
  "data": {
    "id": 1,
    "content": "I'm experiencing the same issue",
    "postId": 1,
    "createdById": 1,
    "createdByUsername": "john_doe",
    "createdAt": "2025-11-27T13:00:00",
    "updatedAt": "2025-11-27T13:00:00"
  },
  "timestamp": "2025-11-27T13:00:00"
}
```

---

### 15. Update Comment
**PUT** `/api/comments/{id}`

Update an existing comment. Only the comment owner can update.

**Headers:** 
```
Authorization: Bearer <token>
```

**Path Parameters:**
- `id`: Comment ID

**Request Body:**
```json
{
  "content": "Updated comment with more information"
}
```

**Response (200):**
```json
{
  "status": 200,
  "message": "Comment updated successfully",
  "data": {
    "id": 1,
    "content": "Updated comment with more information",
    "postId": 1,
    "createdById": 1,
    "createdByUsername": "john_doe",
    "createdAt": "2025-11-27T13:00:00",
    "updatedAt": "2025-11-27T14:00:00"
  },
  "timestamp": "2025-11-27T14:00:00"
}
```

---

### 16. Delete Comment
**DELETE** `/api/comments/{id}`

Delete a comment. Only the comment owner or admin can delete.

**Headers:** 
```
Authorization: Bearer <token>
```

**Path Parameters:**
- `id`: Comment ID

**Response (204):**
No content returned on successful deletion.

---

## Dashboard Endpoints

### 17. Get User Dashboard Summary
**GET** `/api/dashboard/user/summary`

Retrieve dashboard metrics for the authenticated user.

**Headers:** 
```
Authorization: Bearer <token>
```

**Required Role:** Any authenticated user

**Response (200):**
```json
{
  "status": 200,
  "message": "User dashboard summary retrieved successfully",
  "data": {
    "totalPosts": 25,
    "activeIssues": 8,
    "resolvedIssues": 15,
    "draftPosts": 2,
    "totalComments": 42,
    "postsThisMonth": 5,
    "resolutionRate": 60.0
  },
  "timestamp": "2025-11-27T15:00:00"
}
```

**Metrics Explained:**
- `totalPosts`: All posts created by the user
- `activeIssues`: Posts with APPROVED or PENDING_APPROVAL status
- `resolvedIssues`: Posts with RESOLVED status
- `draftPosts`: Posts with DRAFT status
- `totalComments`: Total comments created by the user
- `postsThisMonth`: Posts created in the current month
- `resolutionRate`: Percentage of resolved posts (resolved/total × 100)

---

### 18. Get Admin Dashboard Summary
**GET** `/api/dashboard/admin/summary`

Retrieve system-wide dashboard metrics (Admin only).

**Headers:** 
```
Authorization: Bearer <token>
```

**Required Role:** `ROLE_ADMIN`

**Response (200):**
```json
{
  "status": 200,
  "message": "Admin dashboard summary retrieved successfully",
  "data": {
    "totalUsers": 150,
    "newUsersThisMonth": 12,
    "activeUsers": 85,
    "adminCount": 5,
    "totalPosts": 450,
    "postsThisMonth": 45,
    "pendingApprovals": 8,
    "resolvedCount": 320,
    "totalComments": 1250,
    "commentsThisMonth": 125,
    "avgCommentsPerPost": 2.78,
    "approvalRate": 92.5,
    "resolutionTimeHours": 48.5,
    "userEngagementRate": 56.67
  },
  "timestamp": "2025-11-27T15:00:00"
}
```

**Metrics Explained:**

**User Statistics:**
- `totalUsers`: Total registered users
- `newUsersThisMonth`: Users registered in current month
- `activeUsers`: Users who created posts/comments this month
- `adminCount`: Total number of admin users

**Post Statistics:**
- `totalPosts`: All posts in the system
- `postsThisMonth`: Posts created in current month
- `pendingApprovals`: Posts with PENDING_APPROVAL status
- `resolvedCount`: Posts with RESOLVED status

**Comment Statistics:**
- `totalComments`: All comments in the system
- `commentsThisMonth`: Comments created in current month
- `avgCommentsPerPost`: Average number of comments per post

**System Health Metrics:**
- `approvalRate`: (Approved / (Approved + Rejected)) × 100
- `resolutionTimeHours`: Average time to resolve issues in hours
- `userEngagementRate`: (Active Users / Total Users) × 100

---

## Common Frontend Integration Examples

### JavaScript/Fetch Example

```javascript
// Login
async function login(username, password) {
  const response = await fetch('http://localhost:8080/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ username, password })
  });
  const result = await response.json();
  if (result.status === 200) {
    localStorage.setItem('token', result.data.token);
    localStorage.setItem('username', result.data.username);
    localStorage.setItem('roles', JSON.stringify(result.data.roles));
  }
  return result;
}

// Get Posts with Pagination
async function getPosts(page = 0, size = 10) {
  const token = localStorage.getItem('token');
  const response = await fetch(
    `http://localhost:8080/api/posts?page=${page}&size=${size}`,
    {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }
  );
  return await response.json();
}

// Create Post
async function createPost(postData) {
  const token = localStorage.getItem('token');
  const response = await fetch('http://localhost:8080/api/posts', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(postData)
  });
  return await response.json();
}

// Handle Token Expiration
async function apiCall(url, options = {}) {
  const response = await fetch(url, options);
  if (response.status === 401) {
    // Token expired, redirect to login
    localStorage.clear();
    window.location.href = '/login';
  }
  return await response.json();
}
```

---

## Important Notes

1. **Token Expiration:** JWT tokens expire after 24 hours. Frontend should handle 401 errors by redirecting to login.

2. **Email Validation:** Registration only accepts emails ending with `@infinite.com`.

3. **Role-Based Access:**
   - Public: `/auth/*`
   - Authenticated: `/api/posts/*`, `/api/comments/*`, `/api/dashboard/user/*`
   - Admin Only: `/api/users`, `/api/roles`, `/api/dashboard/admin/*`

4. **Pagination:** Default page size is 10. Maximum recommended is 100.

5. **Post Status Flow:**
   - User creates post → DRAFT
   - User submits → PENDING_APPROVAL
   - Admin approves → APPROVED
   - Admin rejects → REJECTED
   - Admin/User resolves → RESOLVED

6. **Security:**
   - All endpoints except `/auth/*` require JWT token
   - Post/Comment owners can edit/delete their own content
   - Admins can perform all operations

7. **CORS:** API supports CORS for frontend integration (configured in backend).

---

## Support

For issues or questions, contact the backend development team.

**Environment Variables Required:**
- `JWT_SECRET`: Secret key for JWT signing
- `JWT_EXPIRATION`: Token expiration time in milliseconds (default: 86400000)

