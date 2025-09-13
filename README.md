# Motadata-Task
Motadata - Task

AI-Assisted Knowledge Base Search   
--------------------------------------
This project implements an AI-assisted knowledge base search system where users can submit technical questions and receive summarized answers.

Event-to-Insight System: Each user query is treated as an "event".

The system enriches the event with AI-driven insights by fetching knowledge from external sources (simulated knowledge base).

Currently integrated with Wikipedia API for real-time answers.

Initially attempted DuckDuckGo API, but it was deprecated for this use-case, so the system now relies on Wikipedia’s summary API.

--------------
⚙️ Tech Stack
--------------
Java 17

Spring Boot 3 (REST API)

RestTemplate (for API calls)

Maven (dependency management)

-------------------------------

1. Clone Repository

   git clone https://github.com/Bhagyesh7621/Motadata-Task.git
   cd Motadata-Task


2. Build & Run

      mvn clean install

      mvn spring-boot:run


3. Test API

Endpoint: POST /search-query

Request Body:

{
"query": "RabbitMQ"
}

Sample Response:

{
"query": "RabbitMQ",
"ai_summary_answer": "RabbitMQ is an open-source message broker..."
}

📂 Core Functionality

Accepts a user query as input.

Calls Wikipedia API with proper User-Agent.

Returns summarized answer.

Follows assignment guidelines for AI-Assisted Knowledge Base Search.

⚠️ Assumptions

No persistence layer needed.

Queries are simple technical keywords.

Only Wikipedia used for knowledge base.


🔮 Future Enhancements

Add database to store query history.

Add fallback APIs (DuckDuckGo, StackOverflow).

Add frontend dashboard for search & results.

Enhance AI summarization logic.



-----------------------------------------------

🤖 AI Code Assistant Usage Log
1. Prompt

"AI-Assisted Knowledge Base Search: I want to build POST /search-query API that takes 'query' key and returns summarized answer using free API."

AI Response
Suggested integrating DuckDuckGo API or Wikipedia API, and provided sample Spring Boot controller code using RestTemplate.

Action Taken
Implemented DuckDuckGo first, but later switched to Wikipedia API for reliable results.

2. Prompt

"403 Forbidden error when calling Wikipedia API with RestTemplate"

AI Response
Explained that Wikipedia requires a User-Agent header, and showed how to configure RestTemplate with an interceptor to add this header.

Action Taken
Implemented the fix → API calls started working successfully.

3. Prompt

"Give me README.md file for my AI Knowledge Base Search project using Wikipedia API"

AI Response
Generated a structured README including:

Project Overview

Setup Instructions

API Request/Response samples

Software Design Choices

AI Usage Log

Future Enhancements

Action Taken
Adopted and customized README for my project submission.