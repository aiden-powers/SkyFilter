import os
import subprocess

def get_commit_count():
    result = subprocess.run(["git", "rev-list", "--count", "HEAD"], capture_output=True, text=True)
    return result.stdout.strip()

def get_fabric_version():
    with open("gradle.properties", "r") as f:
        lines = f.readlines()
    for line in lines:
        if "fabric_version" in line:
            return line.split("=")[1].strip()

def change_gradle_properties(mod_version):
    with open("gradle.properties", "r") as f:
        lines = f.readlines()
    with open("gradle.properties", "w") as f:
        for line in lines:
            if "mod_version=" in line:
                f.write(f'mod_version={mod_version}\n')
            else:
                f.write(line)
    print("Updated gradle.properties")

def change_fabric_mod_json(mod_version):
    with open("src/main/resources/fabric.mod.json", "r") as f:
        lines = f.readlines()
    with open("src/main/resources/fabric.mod.json", "w") as f:
        for line in lines:
            if "version" in line:
                f.write(f'    "version": "{mod_version}",\n')
            else:
                f.write(line)
    print("Updated fabric.mod.json")

def change_mod_version(mod_version):
    change_gradle_properties(mod_version)
    change_fabric_mod_json(mod_version)

if __name__ == "__main__":
    realversion = "1.0"
    commit_count = get_commit_count()
    mod_version = realversion + "." + commit_count
    fabric = get_fabric_version()
    change_mod_version(mod_version + "+" + fabric)
    print(f"Mod version updated to {mod_version + '+' + fabric}")