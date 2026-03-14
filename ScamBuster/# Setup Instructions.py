#!/usr/bin/env python3
"""Setup script for ScamBuster ML components"""

import subprocess
import sys
import os

def run_command(cmd):
    """Execute shell command"""
    result = subprocess.run(cmd, shell=True, capture_output=True, text=True)
    if result.returncode != 0:
        print(f"Error running: {cmd}")
        print(result.stderr)
        return False
    print(result.stdout)
    return True

def main():
    # Install Git LFS
    print("Installing Git LFS...")
    run_command("git lfs install")
    
    # Clone the model repository
    print("Cloning model repository...")
    model_url = "https://huggingface.co/mradermacher/Llama-3.3-8B-Instruct-128K-i1-GGUF"
    run_command(f"git clone {model_url}")
    
    # Install required packages
    print("Installing Python packages...")
    run_command("pip install huggingface-hub google-cloud-aiplatform")
    
    # Login to Hugging Face (interactive)
    print("Please login to Hugging Face:")
    run_command("huggingface-cli login")
    
    # Initialize Vertex AI
    print("Initializing Vertex AI...")
    try:
        import vertexai
        project_id = os.getenv('GOOGLE_CLOUD_PROJECT', 'YOUR_PROJECT_ID')
        vertexai.init(project=project_id, location="us-central1")
        print(f"Vertex AI initialized with project: {project_id}")
    except ImportError:
        print("Vertex AI not available. Install with: pip install google-cloud-aiplatform")
    
if __name__ == "__main__":
    main()