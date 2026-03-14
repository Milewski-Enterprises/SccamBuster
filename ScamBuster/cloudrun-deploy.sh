#!/bin/bash
# Deploy to Google Cloud Run (free)

PROJECT_ID="your-project-id"
SERVICE_NAME="scambuster"
REGION="us-central1"

# Build and push image
gcloud builds submit --tag gcr.io/$PROJECT_ID/$SERVICE_NAME

# Deploy to Cloud Run
gcloud run deploy $SERVICE_NAME \
  --image gcr.io/$PROJECT_ID/$SERVICE_NAME \
  --platform managed \
  --region $REGION \
  --set-env-vars GITHUB_TOKEN=$GITHUB_TOKEN \
  --memory 512Mi \
  --timeout 3600
