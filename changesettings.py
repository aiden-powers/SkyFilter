import os

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

def change_fabric_mod_json(mod_version):
    with open("src/main/resources/fabric.mod.json", "r") as f:
        lines = f.readlines()
    with open("src/main/resources/fabric.mod.json", "w") as f:
        for line in lines:
            if "version" in line:
                f.write(f'    "version": "{mod_version}",\n')
            else:
                f.write(line)

def change_mod_name(mod_name):
    with open("src/main/resources/fabric.mod.json", "r") as f:
        lines = f.readlines()
    with open("src/main/resources/fabric.mod.json", "w") as f:
        for line in lines:
            if "name" in line:
                f.write(f'    "name": "{mod_name}",\n')
            elif '"id"' in line:
                f.write(f'    "id": "{mod_name.lower().replace(" ", "_")}",\n')
            else:
                f.write(line)
    ## gradle.properties has archives_base_name which needs to be changed
    with open("gradle.properties", "r") as f:
        lines = f.readlines()
    with open("gradle.properties", "w") as f:
        for line in lines:
            if "archives_base_name=" in line:
                f.write(f'archives_base_name={mod_name.lower().replace(" ", "_")}\n')
            else:
                f.write(line)

def change_mod_description(mod_description):
    with open("src/main/resources/fabric.mod.json", "r") as f:
        lines = f.readlines()
    with open("src/main/resources/fabric.mod.json", "w") as f:
        for line in lines:
            if "description" in line:
                f.write(f'    "description": "{mod_description}",\n')
            else:
                f.write(line)

def change_mod_version(mod_version):
    change_gradle_properties(mod_version)
    change_fabric_mod_json(mod_version)


if __name__ == "__main__":
    mod_version = "v1"
    fabric = get_fabric_version()
    change_mod_version(fabric + "+" + mod_version)
    change_mod_name("Skyfilter")
    change_mod_description("Filters out easy-to-detect scam/spam messages in chat.")