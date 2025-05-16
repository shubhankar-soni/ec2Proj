# AWS EC2 Instance Metadata Fetcher (Java)

This project provides a Java utility to query all available metadata of an Amazon Web Services (AWS) EC2 instance and output the result in a nested JSON format.

## Overview

This tool connects to the AWS instance metadata service endpoint (`http://169.254.169.254/latest/meta-data/`) and recursively fetches all available metadata entries and their corresponding values. The output is a JSON string that mirrors the hierarchical structure of the metadata.

## Prerequisites

- Java Development Kit (JDK) installed.

## Getting Started

1.  **Clone the repository** (once you've created it on GitHub).
    ```bash
    git clone <repository-url>
    cd <repository-name>
    ```

2.  **Compile the Java code.**
    ```bash
    javac src/main/java/com/example/EC2MetadataFetcher.java
    ```
    *(Note: If you prefer using a build tool like Maven or Gradle, you can set up the project accordingly and use the build commands for those tools.)*

3.  **Run the application** (from within an AWS EC2 instance).
    ```bash
    java com.example.EC2MetadataFetcher
    ```

## Output

The application will print all available instance metadata as a nested JSON string to the console. The structure of the JSON output will reflect the paths available under `http://169.254.169.254/latest/meta-data/`. For example, you might see sections like `ami-id`, `hostname`, `instance-type`, and nested objects for entries like `block-device-mapping` and `iam`.

```json
{
  "ami-id": "ami-xxxxxxxxxxxxxxxxx",
  "ami-launch-index": "0",
  "ami-manifest-path": "(...)",
  "block-device-mapping": {
    "ami": "(...)",
    "root": "/dev/xvda"
  },
  "hostname": "ip-10-0-1-10.ap-south-1.compute.internal",
  "iam": {
    "info": "(...)",
    "security-credentials": {
      "Your-Instance-Role": "(...)"
    }
  },
  "instance-action": "none",
  "instance-id": "i-xxxxxxxxxxxxxxxxx",
  "instance-type": "t2.micro",
  "local-hostname": "ip-10-0-1-10.ap-south-1.compute.internal",
  "local-ipv4": "10.0.1.10",
  "mac": "xx:xx:xx:xx:xx:xx",
  "placement": {
    "availability-zone": "ap-south-1a"
  },
  "profile": "default-paravirtual",
  "public-hostname": "ec2-52-xx-xx-xx.ap-south-1.compute.amazonaws.com",
  "public-ipv4": "52.xx.xx.xx",
  "public-keys": {},
  "reservation-id": "r-xxxxxxxxxxxxxxxxx",
  "security-groups": [
    "your-security-group"
  ],
  "services": {
    "domain": "amazonaws.com",
    "ec2": {
      "local-ipv4": "169.254.169.254",
      "local-hostname": "ip-169-254-169-254.ap-south-1.compute.amazonaws.com"
    }
  }
}
