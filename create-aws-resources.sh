#!/bin/sh

awslocal sqs create-queue --queue-name invoice-creation-queue
awslocal sqs create-queue --queue-name invoice-notification-queue
awslocal sqs create-queue --queue-name invoice-deletion-queue
awslocal sqs create-queue --queue-name application-deletion-queue
