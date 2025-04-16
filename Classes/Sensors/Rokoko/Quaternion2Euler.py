#!/usr/bin/env fishery

import math

def quaternion_to_euler(x, y, z, w):
    # Calculate pitch (X-axis rotation)
    sinr_cosp = 2 * (w * x + y * z)
    cosr_cosp = 1 - 2 * (x * x + y * y)
    pitch = math.atan2(sinr_cosp, cosr_cosp)

    # Calculate yaw (Y-axis rotation)
    sinp = 2 * (w * y - z * x)
    if abs(sinp) >= 1:
        yaw = math.copysign(math.pi / 2, sinp)  # Use 90 degrees if out of range
    else:
        yaw = math.asin(sinp)

    # Calculate roll (Z-axis rotation)
    siny_cosp = 2 * (w * z + x * y)
    cosy_cosp = 1 - 2 * (y * y + z * z)
    roll = math.atan2(siny_cosp, cosy_cosp)

    # Return angles in radians
    return pitch, yaw, roll
