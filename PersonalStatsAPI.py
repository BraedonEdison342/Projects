import requests
import os
import csv
import sqlite3

# General setup from riot game api documents
api_key = "PUT UR KEY HERE"
headers = {
    "X-Riot-Token": api_key
}
endpoint = "https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/GamatatsuB?api_key=RGAPI-4bda787e-16ab-444d-bbd1-69ae876dd797"
response = requests.get(endpoint, headers=headers)
entry = response.json()
puuid = entry['puuid']


#---------------- Determining which match data to get -----------------------#

# endpoint = f"https://na1.api.riotgames.com/lol/league/v4/entries/by-summoner/{encrypted_summoner_id}"
endpoint = f"https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/{puuid}/ids"

# API request
response = requests.get(endpoint, headers=headers)

# Error check
if response.status_code == 200:
    league_entries = response.json()
else:
    print("Error:", response.status_code)

match_id = league_entries[0]            # change the int here for which match to get '0' being most recent
print(match_id)

#------------ Defining csv values ------------------#

endpoint = f"https://americas.api.riotgames.com/lol/match/v5/matches/{match_id}"
response = requests.get(endpoint, headers=headers)
match_info = response.json()
game_time = match_info['info']['gameDuration'] / 60
match_info = match_info['info']['participants']



player_index = None
player_puuid = None
player_role = None
for index, particiapnt in enumerate(match_info):             # Getting player role and personal ID from game
    for key, value in particiapnt.items():
        if value == puuid:
            player_puuid = puuid
            player_role = match_info[index]['role']
            player_index = index
            break

name = match_info[player_index]['championName']
gameC = " ,"
kills = match_info[player_index]['kills']
assists = match_info[player_index]['assists']
deaths = match_info[player_index]['deaths']
try:
    kda = (kills + assists) / deaths
except Exception as e:
    kda = "Perfect"
cs = match_info[player_index]['totalMinionsKilled']
win_loss = match_info[player_index]['win']
role = match_info[player_index]['role']
support = None
csat10 = None
cspm = cs / game_time

for index, particiapnt in enumerate(match_info):
    for key, value in particiapnt.items():            
        if value == "SUPPORT" and match_info[index]['win'] == win_loss:        # Getting player support 
            support = particiapnt['championName']




#---- Enemy variable list ---#
En_name = None
En_cs = None
En_support = None
en_index = None
En_csat10 = None

for index, particiapnt in enumerate(match_info):
    for key, value in particiapnt.items():
        if value == player_role and match_info[index]['puuid'] != player_puuid:
            en_index = index
            En_name = match_info[index]['championName']                                  # enemy champion
            En_cs = match_info[index]['totalMinionsKilled']

for index, particiapnt in enumerate(match_info):
    for key, value in particiapnt.items(): 
        if value == 'SUPPORT' and match_info[index]['win'] == match_info[en_index]['win']:            # enemy support
            En_support = match_info[index]['championName']



endpoint = f"https://americas.api.riotgames.com/lol/match/v5/matches/{match_id}"
response = requests.get(endpoint, headers=headers)
match_info = response.json()


for i in range(0,10):
    temp_puuid = match_info['info']['participants'][i]['puuid']
    if player_puuid == temp_puuid:
        player_csat10 = match_info['info']['participants'][i]['challenges']['laneMinionsFirst10Minutes']         # player cs @ 10
print(player_csat10)

for i in range(0,10):
    win = match_info['info']['participants'][i]['win']
    if win != win_loss and match_info['info']['participants'][i]['role'] == 'CARRY':
        En_csat10 = match_info['info']['participants'][i]['challenges']['laneMinionsFirst10Minutes']               # enemy cs @ 10
print(En_csat10)



#---------- File Write -------------#

# CSV style
file_path = 'C:/Users/Braedon/Desktop/LeagueAPI/PreviousGame.csv'
data = [
    [name, support, ' ', kda, player_csat10, En_name, En_support, En_csat10, cs, En_cs, win_loss, game_time, cspm]
]

with open(file_path, 'w', newline='') as file:
    writer = csv.writer(file)
    writer.writerows(data)

# Open the CSV file and read its contents
csv_file_path = r'C:\Users\Braedon\Desktop\LeagueAPI\PreviousGame.csv'
with open(csv_file_path, 'r') as file:
    reader = csv.reader(file)
    data = list(reader)

# Connect to the SQLite database
database_path = r'C:\Users\Braedon\Desktop\LeagueAPI\PersonalStats.db'
connection = sqlite3.connect(database_path)
cursor = connection.cursor()

# Generate and execute the SQL insert statements to insert the data
insert_query = "INSERT INTO Stats VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
cursor.executemany(insert_query, data)

# Commit the changes and close the database connection
connection.commit()
connection.close()





