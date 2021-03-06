import sys
import socket
import traceback
import uuid
import time

from concurrent.futures import ThreadPoolExecutor


PORT = 3439
address = ('<broadcast>', PORT)

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

mac = uuid.getnode()
hostname = socket.gethostname()
hostname_len = len(hostname)

pool = ThreadPoolExecutor(max_workers=1)

while True:
    try:
        timestamp = int(time.time())
        s.sendto(','.join([str(mac), str(hostname_len), hostname, str(timestamp)]).encode('utf8'), address)
        pool.submit(print, 'Send broadcast...')
        time.sleep(5)
    except KeyboardInterrupt:
        traceback.print_exc()
        s.close()
        sys.exit(0)

