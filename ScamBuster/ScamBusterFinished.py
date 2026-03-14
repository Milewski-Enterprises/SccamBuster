"""Connect model with mcp tools in Python
# Run this python script
> pip install openai-agents==0.*
> python <this-script-path>.py
"""
import asyncio
import logging
import os
import sys

from agents import Agent, ModelSettings, OpenAIChatCompletionsModel, Runner, set_tracing_disabled
from agents.mcp import MCPServerStdio
from openai import AsyncOpenAI


async def main():
    # Check for required environment variable
    if not os.environ.get("GITHUB_TOKEN"):
        print("Error: GITHUB_TOKEN environment variable is not set.")
        sys.exit(1)

    # To authenticate with the model you will need to generate a personal access token (PAT) in your GitHub settings.
    # Create your PAT token by following instructions here: https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens
    openai_client = AsyncOpenAI(
        base_url="https://models.github.ai/inference",
        api_key=os.environ["GITHUB_TOKEN"],
        default_query={
            "api-version": "2024-08-01-preview",
        },
    )

    # init MCP servers
    servers = [
    ]

    # setup logger
    logger = logging.getLogger("openai.agents")
    logger.setLevel(logging.DEBUG)
    log_handler = logging.StreamHandler(sys.stdout)
    log_handler.setFormatter(logging.Formatter('[Agent Log]: %(message)s'))
    logger.addHandler(log_handler)
    set_tracing_disabled(True)

    agent = Agent(
        name="agent",
        instructions="You are the Supervisor.\nYour primary responsibilities are to ensure continuous workflow, resolve bottlenecks for agents, optimize processes, verify code integrity and security, and address issues that delay project completion.\n\n# Steps\n1. Monitor the workflow and progress of all agents and processes continuously.\n2. If any agent is stopped or stuck, identify the issue and take action to get them back on track (by providing instructions or assigning appropriate support).\n3. Periodically assess if processes require optimization; recommend or implement changes when needed to improve workflow efficiency.\n4. Verify the integrity and security of code, either by calling a tool or assigning another agent to perform the check, especially when relevant changes occur.\n5. Promptly address any issue that might slow, hinder, or delay project completion, recommending interventions or assigning resources as required.\n\nThink carefully step by step about the current status, agent progress, code health, and opportunities for optimization before recommending actions.\n\n# Output Format\nYour output must ALWAYS include:\n<thinking>\nA detailed reasoning in step-by-step form, explaining your diagnostic process and the evaluation leading to each recommended intervention.\n</thinking>\n<supervisor_action>\nYour recommended intervention, assignment, or next step to ensure workflow progress or resolve issues, in concise actionable language.\n</supervisor_action>\n\nExample:\n<thinking>\nAfter reviewing the workflow, I noticed Agent B has been inactive for 10 minutes, likely due to a missing dependency. Code security was checked yesterday, but two new commits suggest a verification is required. No other major bottlenecks.\n</thinking>\n<supervisor_action>\nAssign troubleshooting task to Agent C for the dependency issue; initiate code integrity check using tool X for the recent commits.\n</supervisor_action>",
        mcp_servers=servers,
        model=OpenAIChatCompletionsModel("openai/gpt-4.1", openai_client),
        model_settings=ModelSettings(
            temperature=1,
            top_p=1,
        ),
        tools=[
        ],
    )

    try:
        for server in servers:
            await server.connect()
        result = await Runner.run(
            agent,
            "Begin supervision."
        )
        print("")
        print("[Model Response] " + str(result.final_output))
    except Exception as e:
        print(f"\nError: {str(e)}")
    finally:
        for server in servers:
            await server.cleanup()

if __name__ == "__main__":
    try:
        asyncio.run(main())
    except asyncio.exceptions.CancelledError:
        # ignore cleanup cancel error
        pass
