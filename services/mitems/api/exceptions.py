class ComponentDoesNotExist(Exception):
    """Item or element with the given slug does not exist"""


class ImportingFailed(Exception):
    """There was an error in the process of importing new data to Mitems"""
